package com.buffalo.controlefinancas.ui.bottomsheet

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
import android.widget.ViewSwitcher
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.buffalo.controlefinancas.R
import com.buffalo.controlefinancas.ui.adapter.CitiesAdapter
import com.buffalo.controlefinancas.util.MapElement
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern

class BottomSheetCities(var listener : Listener, var cities: MutableList<String>) : BottomSheetDialogFragment(), CitiesAdapter.Listener, MapElement {
    private var mView: View? = null
    private var mRecyclerMotivos: RecyclerView? = null
    private var mViewSwitcher: ViewSwitcher? = null
    private var editText : EditText? = null
    private var adapter : CitiesAdapter? = null
    var bottonShetBahavior : BottomSheetBehavior<View>? = null

    private var expenseList: MutableList<String>? = ArrayList()
    private var auxList: MutableList<String>? = ArrayList()

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
            .inflate(R.layout.botton_sheet_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mView = view

        // AQUI FAZ O MAGIA DO BOTTOM SHEET NA TELA TODA
        bottonShetBahavior = BottomSheetBehavior.from(view.parent as View)
        bottonShetBahavior?.state = BottomSheetBehavior.STATE_EXPANDED

//        val linarLayout = mView?.findViewById<LinearLayout>(R.id.root_view)
//        linarLayout?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels - 100
//        AQUI ACABA A MAGIA DO BOTTOM SHEET NA TELA TODA

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
        editText = mView!!.findViewById(R.id.edit_text_filter)

        val lAnimationIn = AnimationUtils.loadAnimation(activity, R.anim.slide_in_left)
        lAnimationIn.interpolator = OvershootInterpolator()
        mViewSwitcher?.inAnimation = lAnimationIn
        val lAnimationOut = AnimationUtils.loadAnimation(activity, R.anim.slide_out_right)
        lAnimationOut.interpolator = AccelerateDecelerateInterpolator()
        mViewSwitcher?.outAnimation = lAnimationOut
    }

    override fun mapActionComponents() {
        auxList = cities
        auxList?.let {
            expenseList?.addAll(it)
        }
        adapter = expenseList?.let { CitiesAdapter(it, this) }
        mRecyclerMotivos!!.adapter = adapter
        showViewMotivos()

        editText?.addTextChangedListener {
            filterList(it.toString())
        }
    }

    fun deAccent(str: String?): String? {
        val nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD)
        val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(nfdNormalizedString).replaceAll("")
    }


    private fun filterList(text: String) {
        val filterList = auxList?.filter {
            it.uppercase().contains(text.uppercase()) ||
                    deAccent(it)!!.uppercase().contains(text.uppercase())
        }

        if (filterList.isNullOrEmpty().not()) {
            expenseList?.clear()
            expenseList?.addAll(filterList!!)
            adapter?.notifyDataSetChanged()
        } else {
            if (text.isEmpty()) {
                expenseList?.clear()
                expenseList?.addAll(auxList!!)
                adapter?.notifyDataSetChanged()
            } else {
                expenseList?.clear()
                adapter?.notifyDataSetChanged()
            }
        }
    }

    interface Listener {
        fun onItemClick(aObject: String?)
    }

    companion object {
        private const val TAG = "BottonSheetDialogFragment"
        fun newInstance(listener : Listener, arrayList: MutableList<String>): BottomSheetCities {
            val args = Bundle()
            val fragment = BottomSheetCities(listener, arrayList)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onItemClick(aAtividade: String?) {
        listener.onItemClick(aAtividade)
        dismissAllowingStateLoss()
    }
}