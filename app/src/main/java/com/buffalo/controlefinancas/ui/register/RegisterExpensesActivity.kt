package com.buffalo.controlefinancas.ui.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.buffalo.controlefinancas.database.ExpenseDao
import com.buffalo.controlefinancas.databinding.ActivityRegisterBinding
import com.buffalo.controlefinancas.model.*
import com.buffalo.controlefinancas.ui.bottomsheet.BottomSheetCities
import com.buffalo.controlefinancas.ui.bottomsheet.BottomSheetExpense
import com.buffalo.controlefinancas.ui.bottomsheet.BottomSheetRegisterExpenseType
import com.buffalo.controlefinancas.ui.bottomsheet.BottomSheetStates
import com.buffalo.controlefinancas.util.*
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.*

class RegisterExpensesActivity : AppCompatActivity(), MapElement, BottomSheetExpense.Listener, BottomSheetStates.Listener, BottomSheetCities.Listener, BottomSheetRegisterExpenseType.Listener {

    private lateinit var binding: ActivityRegisterBinding
    private var mExpenseType : ExpenseType? = null
    private var states : States? = null
    private var mState : State? = null
    private var mCity : String? = null
    private var calendar : Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapComponents()
        showDisplayValues()
        mapActionComponents()
        loadState()
        showDisplayInfoLocation()
    }

    private fun loadState() {
        try {
            val obj = JSONObject(loadJSONFromAsset()!!)
            val gson = Gson()
            val jsonStates = gson.fromJson(obj.toString(), States::class.java)
            states = jsonStates
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromAsset(): String? {
        val json: String? = try {
            val inputStrem: InputStream = assets.open("cidades_estados.json")
            val size: Int = inputStrem.available()
            val buffer = ByteArray(size)
            inputStrem.read(buffer)
            inputStrem.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    private fun showDisplayInfoLocation() {
        mState = states?.states?.get(23)
        mCity = mState?.cities?.get(0)
        binding.editTextStates.setText(mState?.name)
        binding.editTextCities.setText(mCity)
    }

    private fun showBottonRegisterExpenseType() {
        val lScheduleOptionsFragment: BottomSheetRegisterExpenseType =
            BottomSheetRegisterExpenseType.newInstance(this)
        lScheduleOptionsFragment.show(supportFragmentManager, null)
    }

    private fun showBottonSheet() {
        val lScheduleOptionsFragment: BottomSheetExpense =
            BottomSheetExpense.newInstance(this)
        lScheduleOptionsFragment.show(supportFragmentManager, null)
    }

    private fun showBottonSheetState() {
        val lScheduleOptionsFragment: BottomSheetStates =
            BottomSheetStates.newInstance(this, states?.states!!)
        lScheduleOptionsFragment.show(supportFragmentManager, null)
    }

    private fun showBottonSheetCities() {
        val lScheduleOptionsFragment: BottomSheetCities =
            BottomSheetCities.newInstance(this, mState?.cities!!)
        lScheduleOptionsFragment.show(supportFragmentManager, null)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showDisplayValues() {
        binding.editTextDate.setText(DateUtil.data("dd/MM/yyyy HH:mm", calendar.time))
    }

    override fun mapComponents() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    @SuppressLint("SetTextI18n")
    override fun mapActionComponents() {
        binding.editTextValue.addTextChangedListener(MonetaryUtil(binding.editTextValue))
        binding.editTextLiter.addTextChangedListener(LiterUtil(binding.editTextLiter))
        binding.editTextExpenseType.setOnClickListener { showBottonSheet() }
        binding.editTextStates.setOnClickListener { showBottonSheetState() }
        binding.editTextCities.setOnClickListener { showBottonSheetCities() }
        binding.editTextDate.setOnClickListener { showDatePicker() }

        binding.register.setOnClickListener {
            if (registerExpense()) {
                val expense = Expense().apply {
                    this.expenseType = mExpenseType
                    this.descricao = binding.editTextDescription.text.toString()
                    this.literage = binding.editTextLiter.text.toString().valueToDouble()
                    this.amount = binding.editTextValue.text.toString().valueToDouble()
                    this.numberTicket = binding.editTextNoteNumber.toString()
                    this.dataRegiter = calendar.time
                    this.state = mState!!.sigla
                    this.city = mCity
                }

                val id = ExpenseDao.save(expense)
                if(id != 0L) Toasty.success(this, "Despesa registrada com sucesso", Toasty.LENGTH_LONG).show() else Toasty.error(this, "Não foi possivel salvar a despesa, tente novamente mais tarde", Toasty.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun registerExpense() : Boolean {
        binding.editTextExpenseType.error = null
        if (mExpenseType == null) {
            binding.editTextExpenseType.error = "Informe o tipo de despesa"
            return false
        }

        binding.editTextDescription.error = null
        if (binding.editTextDescription.text!!.isEmpty()) {
            binding.editTextDescription.error = "Informe uma descrição"
            return false
        }

        binding.editTextValue.error = null
        val valueMonetary = binding.editTextValue.text.toString().valueToDouble()
        if (valueMonetary == 0.0
        ) {
            binding.editTextValue.error = "Informe o valor da despesa"
            return false
        }

        if(mExpenseType!!.descricao.equals("Combustível")) {
            val liter = binding.editTextLiter.text.toString().valueToDouble()
            if(liter > 0) {
                binding.editTextLiter.error = "Informe a litragem de combustível"
            }
        }

        binding.editTextStates.error = null
        if (binding.editTextStates.text!!.isEmpty()) {
            binding.editTextStates.error = "Informe o estado"
            return false
        }

        binding.editTextCities.error = null
        if (binding.editTextCities.text!!.isEmpty()) {
            binding.editTextCities.error = "Informe a cidade"
            return false
        }
        return true
    }

    private fun showDatePicker() {
        val c = calendar
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, _year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, _year)
            binding.editTextDate.setText(DateUtil.data("dd/MM/yyyy HH:mm", calendar.time))
            showTimePicker()
        }, year, month, day)
        dpd.datePicker.maxDate = Calendar.getInstance().timeInMillis
        dpd.show()
    }

    fun setExpenseType(newValue : ExpenseType) {
        binding.editTextExpenseType.error = null
        binding.editTextExpenseType.setText(newValue.descricao)
        if(mExpenseType!!.descricao.equals("Combustível")) {
            binding.textImputLiter.visibility = View.VISIBLE
        } else {
            binding.textImputLiter.visibility = View.GONE
        }
    }

    private fun showTimePicker() {
        val c = calendar
        val hourOfDay = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog (this,
            { _: TimePicker?, hour: Int, _minute: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, _minute)
                binding.editTextDate.setText(DateUtil.data("dd/MM/yyyy HH:mm", calendar.time))
            },
                hourOfDay,
                minute,
            true)
            .show()
    }

    override fun onItemClick(aAtividade: ExpenseType?) {
        mExpenseType = aAtividade
        mExpenseType?.let { setExpenseType(it) }
    }

    @SuppressLint("SetTextI18n")
    override fun onItemClick(aObject: State?) {
        mState = aObject
        mCity = null
        binding.editTextStates.setText(aObject?.name)
        binding.editTextCities.setText("")
    }

    override fun onItemClick(aObject: String?) {
        mCity = aObject
        binding.editTextCities.setText(aObject)
    }

    override fun onRegisterExpense(aAtividade: ExpenseType) {
        mExpenseType = aAtividade
        setExpenseType(aAtividade)
    }

    override fun newExpense() {
        showBottonRegisterExpenseType()
    }
}