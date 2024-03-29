package com.buffalo.controlefinancas.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.buffalo.controlefinancas.R
import com.buffalo.controlefinancas.model.Expense
import com.buffalo.controlefinancas.util.ColorUtil
import com.buffalo.controlefinancas.util.DateUtil
import java.text.NumberFormat
import java.util.*


class FilterExpenseAdapter(var listener: Listener, aExpenses: MutableList<Expense>) :
    RecyclerView.Adapter<FilterExpenseAdapter.ViewHolder>() {

    val mData: MutableList<Expense>
    private var mContext: Context? = null

    fun setContext(aContext: Context?) {
        mContext = aContext
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val lViewInflated: View = LayoutInflater.from(parent.context).inflate(R.layout.row_mini_expense, parent, false)
        return ViewHolder(lViewInflated)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val lExpense: Expense = mData[position]
        bindData(holder, lExpense, position)
        holder.itemView.tag = lExpense
        holder.itemView.setOnClickListener {
            listener.onItemClick(lExpense)
        }
    }

    fun Double.formatDoubleMoneyToString(): String {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return numberFormat.format(this)
    }

    fun setTextColorHour(textView: TextView, hexdecimal: String) {
        if (ColorUtil.dominatColor(hexdecimal) > ColorUtil.MEDIA_COLOR) {
            mContext?.let {
                textView.setTextColor(ContextCompat.getColor(it, R.color.divider))
            }
        } else{
            mContext?.let {
                textView.setTextColor(ContextCompat.getColor(it, R.color.white))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(@NonNull aHolder: ViewHolder, aExpense: Expense, position: Int) {
        aExpense.expenseType?.let {
            aHolder.mType.text = it.descricao
        }

        if(aExpense.descricao.isNullOrEmpty()) {
            aHolder.mDescricao.visibility = View.GONE
        } else {
            aHolder.mDescricao.text = aExpense.descricao
        }
        aHolder.mHoraWhy.text = DateUtil.dataFormat("HH:mm", aExpense.dataRegiter!!)
        aHolder.mDataHoraFim.text = DateUtil.dataFormat("dd/MM/yyyy", aExpense.dataRegiter!!)
        aHolder.mValor.text = aExpense.amount!!.formatDoubleMoneyToString()
        aHolder.imageViewCircle.setColorFilter(Color.parseColor(aExpense.expenseType!!.color),PorterDuff.Mode.SRC_IN)
        setTextColorHour(aHolder.mHoraWhy, aExpense.expenseType!!.color!!)
        aHolder.imageLineUp.setBackgroundColor(Color.parseColor(aExpense.expenseType!!.color))
        aHolder.imageLineDown.setBackgroundColor(Color.parseColor(aExpense.expenseType!!.color))

        if(mData.size > 1) {
            if(position == 0) {
                aHolder.imageLineUp.visibility = View.INVISIBLE
                aHolder.imageLineDown.visibility = View.VISIBLE
                aHolder.imageLineDown.background = createColor(mData[position].expenseType!!.color!!, mData[position + 1].expenseType!!.color!!)
            } else if (position == (mData.size - 1)) {
                aHolder.imageLineUp.visibility = View.VISIBLE
                aHolder.imageLineDown.visibility = View.INVISIBLE
            } else {
                aHolder.imageLineDown.background = createColor(mData[position].expenseType!!.color!!, mData[position + 1].expenseType!!.color!!)
                aHolder.imageLineUp.visibility = View.VISIBLE
                aHolder.imageLineDown.visibility = View.VISIBLE
            }
        } else {
            aHolder.imageLineUp.visibility = View.INVISIBLE
            aHolder.imageLineDown.visibility = View.INVISIBLE
        }
        aHolder.mLocalizacao.text = "${aExpense.city} - ${aExpense.state}"
    }

    fun createColor(startColor : String, finalCorlor: String): GradientDrawable {
        return  GradientDrawable().apply {
            colors = intArrayOf(
                Color.parseColor(startColor),
                Color.parseColor(startColor),
                Color.parseColor(finalCorlor),
            )
            orientation = GradientDrawable.Orientation.TOP_BOTTOM
            gradientType = GradientDrawable.LINEAR_GRADIENT
            shape = GradientDrawable.RECTANGLE
        }
    }

    fun clear() {
        mData.clear()
        notifyItemRangeChanged(0, mData.size)
    }

    fun update() {
        notifyItemRangeChanged(0, mData.size)
    }

    fun remove(expense: Expense) {
        val removeIndex = mData.indexOf(expense)
        mData.removeAt(removeIndex)
        notifyItemRemoved(removeIndex)
        if(mData.size != 0) {
            notifyItemRangeChanged(0, mData.size)
        }
    }

    fun showMsm(): Boolean {
       return mData.size == 0
    }

    interface Listener {
        fun onItemClick(aExpense: Expense?)
    }

    inner class ViewHolder(aItemView: View) : RecyclerView.ViewHolder(aItemView) {
        var mType: TextView
        var mHoraWhy: TextView
        var mDescricao: TextView
        var mDataHoraFim: TextView
        var mLocalizacao: TextView
        var mValor: TextView
        var imageLineUp: View
        var imageLineDown: View
        var imageViewCircle : ImageView

        init {
            mType = aItemView.findViewById(R.id.label_txt_descricao)
            mHoraWhy = aItemView.findViewById(R.id.txt_hora_why)
            mDescricao = aItemView.findViewById(R.id.txt_descricao)
            mValor = aItemView.findViewById(R.id.txt_value)
            mDataHoraFim = aItemView.findViewById(R.id.txt_data_hora_fim)
            imageLineUp  = aItemView.findViewById(R.id.view_linha_up)
            imageLineDown  = aItemView.findViewById(R.id.view_linha_down)
            imageViewCircle = aItemView.findViewById(R.id.img_circulo)
            mLocalizacao = aItemView.findViewById(R.id.txt_locale)
        }
    }

    init {
        mData = aExpenses
    }
}