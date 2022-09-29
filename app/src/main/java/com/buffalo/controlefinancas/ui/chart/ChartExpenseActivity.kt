package com.buffalo.controlefinancas.ui.chart

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.SpannableString
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.buffalo.controlefinancas.R
import com.buffalo.controlefinancas.database.ExpenseDao
import com.buffalo.controlefinancas.databinding.ActivityChartBinding
import com.buffalo.controlefinancas.model.Expense
import com.buffalo.controlefinancas.model.FilterExpenseType
import com.buffalo.controlefinancas.ui.adapter.FilterExpenseTypeAdapter
import com.buffalo.controlefinancas.util.*
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileOutputStream
import java.util.*


class ChartExpenseActivity: AppCompatActivity(), MapElement {

    val STORAGE_PERMISSION_CODE: Int = 100

    private var chart: PieChart? = null
    private var expense : MutableList<Expense> = mutableListOf()
    private var filterExpenses : MutableList<FilterExpenseType> = mutableListOf()
    private lateinit var binding: ActivityChartBinding
    private var filterExpenseTypeAdapter: FilterExpenseTypeAdapter? = null
    private var recyclerView : RecyclerView? = null
    private var totalExpense : Double? = 0.0
    private var startDate: Calendar? = null
    private var finalDate: Calendar? = null
    private var startDateDefault: Calendar? = null
    private var finalDateDefault: Calendar? = null
    private var pieDataSet: PieDataSet? = null
    private var pieData: PieData? = null
    private var listTextColors = ArrayList<Int>()
    private var listColorChart = ArrayList<Int>()

    private fun createSheet() {
        val workbook: Workbook = HSSFWorkbook()
        val sheet: Sheet?
        val EXCEL_SHEET_NAME = "CONTAS"
        sheet = workbook.createSheet(EXCEL_SHEET_NAME)

        var count = 1
        for (item in filterExpenses) {
            count = if(item.expenseType!!.descricao!!.contains("Combustível")) {
                XLSUtil.createCellsFuel(count, workbook, sheet, item.expenses, item.totalValue())
            } else {
                XLSUtil.createCellsNormal(count, workbook, sheet, item.expenses, item.totalValue())
            }
            count += 2
        }

        val filePath = File("${Environment.getExternalStorageDirectory()}/${createFileName()}.xls")
        try {
            if (!filePath.exists()) {
                filePath.createNewFile()
            }
            val fileOutputStream = FileOutputStream(filePath)
            workbook.write(fileOutputStream)
            fileOutputStream.let {
                it.flush()
                it.close()
                shareFile(filePath)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createFileName(): String {
        return "prestacao_contas_${DateUtil.data(Date(), "dd_MM_YYYY_HH_mm")}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapComponents()
        mapActionComponents()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_calendar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                if(binding.filter.containerExpadable.isExpanded) {
                    expandChart()
                } else {
                    colapseChart()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun colapseChart() {
        chart?.centerText = ""
        chart!!.description.isEnabled = true
        pieData?.setValueTextSize(11f)
        pieDataSet?.isUsingSliceColorAsValueLineColor = true
        pieDataSet?.setAutomaticallyDisableSliceSpacing(true)
        pieDataSet?.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieData?.setValueTextColor(Color.BLACK)
        binding.filter.containerExpadable.expand()
    }

    private fun expandChart() {
        createTextColor()
        chart!!.centerText = generateCenterSpannableText(totalExpense!!)
        chart!!.description.isEnabled = false
        pieDataSet?.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
        pieData = PieData(pieDataSet)
        pieData?.setValueTextSize(13f)
        pieData?.setValueTextColors(listTextColors)
        binding.filter.containerExpadable.collapse()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun mapComponents() {
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        startCalendarFilter()
        showDisplayFilter()
        createChart()
    }

    override fun mapActionComponents() {
        binding.filter.editTextStartDate.setOnClickListener {
            DateUtil.showDatePicker(this, startDate!!, binding.filter.editTextStartDate, maxDate = finalDate!!.timeInMillis, minDate = null)
        }

        binding.filter.editTextFinalDate.setOnClickListener {
            DateUtil.showDatePicker(this, finalDate!!, binding.filter.editTextFinalDate, maxDate = finalDateDefault!!.timeInMillis, minDate = startDate?.timeInMillis)
        }

        binding.filter.btnFilter.setOnClickListener {
            filterExpenses.clear()
            expense.clear()
            showDisplayFilter()
            createChart()
            expandChart()
        }

        binding.register.setOnClickListener {
            if (checkPermission()) {
                createSheet()
            } else {
                requestPermission()
            }

        }
    }

    private fun requestPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
                storageActivityResultLauncher.launch(intent)
            } catch (ex : Exception) {
                Log.e("", "", ex)
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                storageActivityResultLauncher.launch(intent)
            }
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ), STORAGE_PERMISSION_CODE)

        }
    }

    var storageActivityResultLauncher : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>() {
            Log.e("", "ActivityResult: ")
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if(Environment.isExternalStorageManager()) {
                    Log.d("", "ActivityResult: Aceitou")
                    createSheet()
                } else {
                    Log.d("", "ActivityResult: Negou")
                    dialog()
                }
            } else {

            }
        }
    )

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == STORAGE_PERMISSION_CODE) {
            if(grantResults.size > 0) {
                var write = grantResults[0] == PackageManager.PERMISSION_GRANTED
                var read = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if(write && read) {
                    Log.d("", "ActivityResult: Aceitou")
                    createSheet()
                } else {
                    Log.d("", "ActivityResult: Negou")
                    dialog()
                }
            }
        }

    }

    fun checkPermission() :  Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager()
        } else {
            var write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            var read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Você não permitiu o acesso a memória de seu dispositivo, deseja revisar as permissões?")
            .setCancelable(false)
            .setPositiveButton("SIM") { dialog, id ->
                startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.parse("package:" + this.packageName)))
                dialog.cancel()
            }
            .setNegativeButton("NÃO") { dialog, id ->
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun startCalendarFilter() {
        val calendar = Calendar.getInstance()
        startDate = DateUtil.getFirstDayMonth(calendar)
        startDateDefault = DateUtil.getFirstDayMonth(calendar)

        finalDate = DateUtil.getLastDayMonth(calendar)
        finalDateDefault = DateUtil.getLastDayMonth(calendar)
        binding.filter.editTextStartDate.setText(DateUtil.data(startDate!!.time, "dd/MM/yyyy HH:mm"))
        binding.filter.editTextFinalDate.setText(DateUtil.data(finalDate!!.time, "dd/MM/yyyy HH:mm"))
    }

    private fun createLegendChart(expenses: MutableList<Expense>) {
        for (item : Expense in expenses) {
            val lFilterExpense = filterExpenses.find { it.expenseType == item.expenseType }
            if (lFilterExpense == null) {
                val filterExpenseType = FilterExpenseType()
                filterExpenseType.addExpense(item)
                filterExpenseType.expenseType = item.expenseType
                filterExpenses.add(filterExpenseType)
            } else {
                lFilterExpense.addExpense(item)
            }
        }
    }

    private fun showDisplayFilter() {
        ExpenseDao.loadByDateExpenseTypeShort(startDate!!.time, finalDate!!.time)?.let {
            expense.addAll(it.toList())
            createLegendChart(expense)
        }

        filterExpenseTypeAdapter = FilterExpenseTypeAdapter(filterExpenses)
        recyclerView = binding.rcAtividade
        recyclerView?.adapter = filterExpenseTypeAdapter
        totalExpense = ExpenseDao.totalExpenses(startDate!!.time, finalDate!!.time)
    }

    private fun createTextColor() {
        for (item: FilterExpenseType in filterExpenses) {
            listTextColors.clear()
            listTextColors.add(ContextCompat.getColor(applicationContext, item.expenseType!!.color!!.dominatColor()))
        }
    }

    private fun createChart() {
        val values = ArrayList<PieEntry>()
        listColorChart.clear()

        for (item: FilterExpenseType in filterExpenses) {
            val entry = PieEntry((item.totalValue().toFloat() * 100) / totalExpense!!.toFloat(), item.expenseType!!.descricao)
            values.add(entry)
            listColorChart.add(ColorTemplate.rgb(item.expenseType!!.color))
        }

        createTextColor()
        pieDataSet = PieDataSet(values, "")
        pieDataSet?.colors = listColorChart
        pieDataSet?.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
        pieDataSet?.setAutomaticallyDisableSliceSpacing(false)

        pieData = PieData(pieDataSet)
        pieData?.setValueTextColors(listTextColors)
        pieData?.setValueTextSize(13f)
        pieData?.setValueFormatter(FormatChart())
        chart = binding.chart

        chart!!.centerText = generateCenterSpannableText(totalExpense!!)
        chart!!.description.isEnabled = false
        chart!!.description.text = generateCenterSpannableText(totalExpense!!).toString()
        chart!!.animateY(1400, Easing.EaseInCirc)
        chart!!.data = pieData
        chart!!.setCenterTextSize(13f)
        chart!!.setDrawEntryLabels(false)
        chart!!.legend.isEnabled = false
        chart!!.invalidate()
    }

    private fun generateCenterSpannableText(amount: Double): SpannableString {
        return SpannableString("Total de gastos\n ${amount.formatDoubleMoneyToString()}")
    }


}



fun String.dominatColor(): Int{
    return if (ColorUtil.dominatColor(this) > ColorUtil.MEDIA_COLOR) {
        R.color.divider
    } else {
        R.color.white
    }
}