package com.buffalo.controlefinancas.ui.bottomsheet

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.EditText
import android.widget.ImageView
import android.widget.ViewSwitcher
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.RecyclerView
import com.buffalo.controlefinancas.R
import com.buffalo.controlefinancas.database.ColorExpenseDAO
import com.buffalo.controlefinancas.model.ColorExpense
import com.buffalo.controlefinancas.model.ExpenseType
import com.buffalo.controlefinancas.ui.adapter.ColorExpenseTypeAdapter
import com.buffalo.controlefinancas.util.MapElement
import com.example.controlefinancas.database.ExpenseTypeDAO
import com.github.clans.fab.FloatingActionButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import es.dmoral.toasty.Toasty
import java.util.*


class BottomSheetRegisterExpenseType(var listener : Listener) : BottomSheetDialogFragment(), ColorExpenseTypeAdapter.Listener, MapElement {
    private var mView: View? = null
    private var mRecyclerMotivos: RecyclerView? = null
    private var mViewSwitcher: ViewSwitcher? = null
    private var editText : EditText? = null
    private var adapter : ColorExpenseTypeAdapter? = null
    private var listColors: MutableList<ColorExpense>? = ArrayList()
    private var floarActionButton: ImageView? = null
    private var colorExpense: ColorExpense? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val lContextThemeWrapper: Context = ContextThemeWrapper(activity, R.style.Theme_ControleFinancas)
        return inflater.cloneInContext(lContextThemeWrapper).inflate(R.layout.botton_sheet_register_expense_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mView = view
        mapComponents()
        mapActionComponents()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showViewMotivos() {
        Handler(Looper.getMainLooper()!!).post { mViewSwitcher!!.showNext() }
    }

    override fun mapComponents() {
        mRecyclerMotivos = mView!!.findViewById(R.id.rc_motivos)
        mViewSwitcher = mView!!.findViewById(R.id.view_switcher)
        editText = mView!!.findViewById(R.id.edit_text_description)
        floarActionButton = mView!!.findViewById(R.id.image_add)

        val lAnimationIn = AnimationUtils.loadAnimation(activity, R.anim.slide_in_left)
        lAnimationIn.interpolator = OvershootInterpolator()
        mViewSwitcher?.inAnimation = lAnimationIn
        val lAnimationOut = AnimationUtils.loadAnimation(activity, R.anim.slide_out_right)
        lAnimationOut.interpolator = AccelerateDecelerateInterpolator()
        mViewSwitcher?.outAnimation = lAnimationOut
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun mapActionComponents() {
        listColors = ColorExpenseDAO.loadAllNotUsed()
        adapter = listColors?.let { ColorExpenseTypeAdapter(it, this) }
        adapter?.setContext(activity)
        mRecyclerMotivos!!.adapter = adapter
        showViewMotivos()

        floarActionButton?.setOnClickListener {
            if (checkFields()) {
                registerExpenseType()
            }
        }
    }

    private fun registerExpenseType() {
        val expenseType = ExpenseType(0,
            editText!!.text.toString().trim(),
            colorExpense!!.hexadecimal
        )

        colorExpense?.let {  color ->
            ColorExpenseDAO.update(color)
        }

        expenseType.let { obj ->
            expenseType._id = ExpenseTypeDAO.save(obj)
        }

        listener.onRegisterExpense(expenseType)
        dismissAllowingStateLoss()
    }

    private fun checkFields() : Boolean {
        if (editText?.text!!.isEmpty()) {
            activity?.let { context -> Toasty.error(context, "Informe uma descrição", Toasty.LENGTH_LONG).show() }
            return false
        }

        if (editText?.text!!.length <= 3) {
            activity?.let { context -> Toasty.error(context, "A descrição deve ter mais de 3 caracteres", Toasty.LENGTH_LONG).show() }
            return false
        }

        if(colorExpense == null) {
            activity?.let { context -> Toasty.error(context, "Selecione uma cor para continuar", Toasty.LENGTH_LONG).show() }
            return false
        }
        return true
    }

    interface Listener {
        fun onRegisterExpense(aAtividade: ExpenseType)
    }

    companion object {
        fun newInstance(listener : Listener): BottomSheetRegisterExpenseType {
            val args = Bundle()
            val fragment = BottomSheetRegisterExpenseType(listener)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onItemClick(aAtividade: ColorExpense?) {
        colorExpense = aAtividade
    }
}