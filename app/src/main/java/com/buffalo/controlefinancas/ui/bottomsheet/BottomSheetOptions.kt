package com.buffalo.controlefinancas.ui.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buffalo.controlefinancas.R
import com.buffalo.controlefinancas.model.Expense
import com.buffalo.controlefinancas.model.props.EOptions
import com.buffalo.controlefinancas.ui.MainActivity
import com.buffalo.controlefinancas.util.MapElement
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOptions(var expense: Expense) : BottomSheetDialogFragment(), MapElement {

    private var mShow: View? = null
    private var mEdit: View? = null
    private var mDelete: View? = null
    private var mCallback: Callback? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val lContextThemeWrapper: Context = androidx.appcompat.view.ContextThemeWrapper(
            activity, R.style.Theme_ControleFinancas
        )
        return inflater.cloneInContext(lContextThemeWrapper)
            .inflate(R.layout.botton_sheet_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapComponents()
        mapActionComponents()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun mapActionComponents() {
        mShow?.setOnClickListener {
            mCallback?.onClick(EOptions.SHOW, expense)
            dismissAllowingStateLoss()
        }
        mEdit?.setOnClickListener {
            mCallback?.onClick(EOptions.EDIT, expense)
            dismissAllowingStateLoss()
        }
        mDelete?.setOnClickListener {
            mCallback?.onClick(EOptions.DELETE, expense)
            dismissAllowingStateLoss()
        }
    }

    override fun mapComponents() {
        mEdit = requireView().findViewById(R.id.tv_edit)
        mDelete = requireView().findViewById(R.id.tv_delete)
    }

    fun setListener(aListener: Callback) {
        mCallback = aListener
    }

    interface Callback {
        fun onClick(optionMenuEnum: EOptions, expense : Expense)
    }

    companion object {
        fun newInstance(expense : Expense): BottomSheetOptions {
            return BottomSheetOptions(expense)
        }
    }
}
