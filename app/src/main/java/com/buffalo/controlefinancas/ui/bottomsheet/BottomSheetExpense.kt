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
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.buffalo.controlefinancas.R
import com.buffalo.controlefinancas.model.ExpenseType
import com.buffalo.controlefinancas.ui.adapter.ExpenseTypeAdapter
import com.buffalo.controlefinancas.util.MapElement
import com.example.controlefinancas.database.ExpenseTypeDAO.loadAll
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern

class BottomSheetExpense(var listener : Listener) : BottomSheetDialogFragment(), ExpenseTypeAdapter.Listener, MapElement {
    private var mView: View? = null
    private var mRecyclerMotivos: RecyclerView? = null
    private var mViewSwitcher: ViewSwitcher? = null
    private var editText : EditText? = null
    private var imageAdd : ImageView? = null
    private var adapter : ExpenseTypeAdapter? = null
    private var bottonShetBahavior : BottomSheetBehavior<View>? = null

    private var expenseList: MutableList<ExpenseType>? = ArrayList()
    private var auxList: MutableList<ExpenseType>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val lContextThemeWrapper: Context = ContextThemeWrapper(
            activity, R.style.Theme_ControleFinancas
        )
        return inflater.cloneInContext(lContextThemeWrapper)
            .inflate(R.layout.botton_sheet_expense_type_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mView = view

        bottonShetBahavior = BottomSheetBehavior.from(view.parent as View)
        bottonShetBahavior?.state = BottomSheetBehavior.STATE_EXPANDED

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
        editText = mView!!.findViewById(R.id.edit_text_expense_type)
        imageAdd = mView!!.findViewById(R.id.image_add)
        val lAnimationIn = AnimationUtils.loadAnimation(activity, R.anim.slide_in_left)
        lAnimationIn.interpolator = OvershootInterpolator()
        mViewSwitcher?.inAnimation = lAnimationIn
        val lAnimationOut = AnimationUtils.loadAnimation(activity, R.anim.slide_out_right)
        lAnimationOut.interpolator = AccelerateDecelerateInterpolator()
        mViewSwitcher?.outAnimation = lAnimationOut
    }

    override fun mapActionComponents() {
        auxList =  loadAll()
        auxList?.let {
            expenseList?.addAll(it)
        }
        adapter = expenseList?.let { ExpenseTypeAdapter(it, this) }
        mRecyclerMotivos!!.adapter = adapter
        showViewMotivos()

        editText?.addTextChangedListener {
            filterList(it.toString())
        }

        imageAdd?.setOnClickListener {
            listener.newExpense()
            dismissAllowingStateLoss()
        }
    }

    private fun deAccent(str: String?): String? {
        val nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD)
        val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(nfdNormalizedString).replaceAll("")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterList(text: String) {
        val filterList = auxList?.filter {
            it.descricao!!.uppercase().contains(text.uppercase()) ||
                    deAccent(it.descricao)!!.uppercase().contains(text.uppercase())
        }

        if (filterList.isNullOrEmpty().not()) {
            expenseList?.clear()
            expenseList?.addAll(filterList!!)
        } else {
            if (text.isEmpty()) {
                expenseList?.clear()
                expenseList?.addAll(auxList!!)
            } else {
                expenseList?.clear()
            }
        }
        adapter?.notifyDataSetChanged()
    }

    interface Listener {
        fun onItemClick(aAtividade: ExpenseType?)
        fun newExpense()
    }

    companion object {
        fun newInstance(listener : Listener): BottomSheetExpense {
            val args = Bundle()
            val fragment = BottomSheetExpense(listener)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onItemClick(aAtividade: ExpenseType?) {
        listener.onItemClick(aAtividade)
        dismissAllowingStateLoss()
    }
}