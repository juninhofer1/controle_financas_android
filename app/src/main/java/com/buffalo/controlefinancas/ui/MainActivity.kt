package com.buffalo.controlefinancas.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.buffalo.controlefinancas.R
import com.buffalo.controlefinancas.components.CollapsibleCalendarAgenda
import com.buffalo.controlefinancas.database.ExpenseDao
import com.buffalo.controlefinancas.databinding.ActivityMainBinding
import com.buffalo.controlefinancas.model.Expense
import com.buffalo.controlefinancas.model.props.EOptions
import com.buffalo.controlefinancas.ui.adapter.ExpenseAdapter
import com.buffalo.controlefinancas.ui.bottomsheet.BottomSheetOptions
import com.buffalo.controlefinancas.ui.chart.ChartExpenseActivity
import com.buffalo.controlefinancas.ui.register.RegisterExpensesActivity
import com.buffalo.controlefinancas.util.ActionMaps
import com.buffalo.controlefinancas.util.DateUtil
import com.buffalo.controlefinancas.util.activity.ActivityUtil
import com.buffalo.controlefinancas.util.calendario.data.Day
import com.buffalo.controlefinancas.util.calendario.data.Event
import com.buffalo.controlefinancas.util.calendario.widget.CollapsibleCalendar
import es.dmoral.toasty.Toasty
import java.util.*

class MainActivity : AppCompatActivity(), ActionMaps, ExpenseAdapter.Listener, BottomSheetOptions.Callback {

    private lateinit var binding: ActivityMainBinding
    private var expense : MutableList<Expense> = mutableListOf()
    private var recyclerView : RecyclerView? = null
    private var mAdapter: ExpenseAdapter? = null
    private var customCalendar: CollapsibleCalendarAgenda? = null
    private var bottomSheetOptions: BottomSheetOptions? = null
    private var selectedDate: Date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapComponents()
        mapComponentActions()
    }

    private fun showDisplayCalendarList() {
        mAdapter = ExpenseAdapter(this, expense)
        mAdapter?.setContext(this)
        recyclerView?.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        createData(selectedDate)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onBackPressed() {
        if(binding.floatingActionButtonAdd.isOpened){
            binding.floatingActionButtonAdd.close(true)
        } else {
            finish()
        }
    }

    private fun openRegisterExpense() {
       ActivityUtil.Builder(this@MainActivity, RegisterExpensesActivity::class.java).build()
    }

    private fun showOptionList(expense: Expense) {
        bottomSheetOptions = BottomSheetOptions.newInstance(expense)
        bottomSheetOptions?.let {
            it.setListener(this)
            it.show(supportFragmentManager, null)
        }
    }

    private fun createData(searchDate: Date) {
        val startDate = Calendar.getInstance()
        startDate.time = searchDate
        startDate.set(Calendar.HOUR_OF_DAY, 0)
        startDate.set(Calendar.MINUTE, 0)
        startDate.set(Calendar.SECOND, 0)

        val finalDate = Calendar.getInstance()
        finalDate.time = searchDate
        finalDate.set(Calendar.HOUR_OF_DAY, 23)
        finalDate.set(Calendar.MINUTE, 59)
        finalDate.set(Calendar.SECOND, 59)

        expense.clear()
        ExpenseDao.loadByDate(startDate.time, finalDate.time)?.let {
            expense.addAll(it.toList())
        }

        if(expense.isNotEmpty()) {
            binding.containerMsm.visibility = View.GONE
        } else {
            binding.containerMsm.visibility = View.VISIBLE
        }

        ExpenseDao.loadAll()?.let {
            updateCalendarEvents(it.toList())
        }

        mAdapter?.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun mapComponents() {
        customCalendar = binding.mcvMonth
        customCalendar?.post { customCalendar?.expand(400) }
        recyclerView = binding.rcAtividade
        showDisplayCalendarList()
    }

    private fun updateCalendarEvents(aEvents: List<Expense?>) {
        val lList: MutableList<Event> = ArrayList<Event>()
        for (lEvent in aEvents) {
            val lCal = Calendar.getInstance()
            lCal.time = lEvent!!.dataRegiter!!
            lList.add(
                Event(
                    lCal[Calendar.YEAR], lCal[Calendar.MONTH],
                    lCal[Calendar.DAY_OF_MONTH]
                )
            )
        }
        customCalendar?.updateEvents(lList)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun mapComponentActions() {
        binding.menuItemAdd.setOnClickListener {
            binding.floatingActionButtonAdd.close(true)
            openRegisterExpense()
        }

        binding.menuItemCreateEx.setOnClickListener {
            binding.floatingActionButtonAdd.close(true)
            ActivityUtil.Builder(this@MainActivity, ChartExpenseActivity::class.java).build()
        }

        binding.menuItemAddEvent.setOnClickListener {
            binding.floatingActionButtonAdd.close(true)
            Toasty.info(applicationContext, "Em breve", Toasty.LENGTH_LONG).show()
        }

        customCalendar?.setCalendarListener(object  : CollapsibleCalendar.CalendarListener{
            override fun onDaySelect() {}
            override fun onItemClick(v: View?) {
                val day: Day? = customCalendar?.selectedDay
                selectedDate = DateUtil.data(day.toString(), "dd/MM/yyyy")
                createData(selectedDate)
                binding.floatingActionButtonAdd.visibility = View.VISIBLE
            }
            override fun onDataUpdate() {}
            override fun onMonthChange() {}
            override fun onWeekChange(position: Int) {}
        })

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0 && binding.floatingActionButtonAdd.visibility == View.INVISIBLE) {
                    binding.floatingActionButtonAdd.visibility = View.VISIBLE
                } else if(dy > 0 && binding.floatingActionButtonAdd.visibility == View.VISIBLE) {
                    binding.floatingActionButtonAdd.visibility = View.INVISIBLE
                }
            }
        })
    }

    override fun onItemClick(aExpense: Expense?) {
        aExpense?.let {
            showOptionList(it)
        }
    }

    private fun deleteExpense(expense: Expense) {
        expense._id?.let { id ->
            ExpenseDao.deleteById(id)
            mAdapter?.let {
                it.remove(expense)
                if(it.mData.size > 3) {
                    binding.floatingActionButtonAdd.visibility = View.VISIBLE
                }
                if (it.showMsm()) {
                    binding.containerMsm.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onClick(optionMenuEnum: EOptions, expense: Expense) {
        when (optionMenuEnum) {
            EOptions.DELETE -> {
                deleteExpense(expense)
            }
            EOptions.EDIT -> {
                ActivityUtil.Builder(this@MainActivity, RegisterExpensesActivity::class.java).build()
            }
            EOptions.SHOW -> {

            }
        }
    }
}