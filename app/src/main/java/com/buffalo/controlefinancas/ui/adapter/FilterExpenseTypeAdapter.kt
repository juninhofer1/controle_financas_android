package com.buffalo.controlefinancas.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import com.buffalo.controlefinancas.R
import com.buffalo.controlefinancas.model.Expense
import com.buffalo.controlefinancas.model.ExpenseType
import com.buffalo.controlefinancas.model.FilterExpenseType
import com.buffalo.controlefinancas.util.formatDoubleMoneyToString
import net.cachapa.expandablelayout.ExpandableLayout

class FilterExpenseTypeAdapter(aExpenseType: MutableList<FilterExpenseType>) :
    RecyclerView.Adapter<FilterExpenseTypeAdapter.ViewHolder>(), FilterExpenseAdapter.Listener {

    private val mData: MutableList<FilterExpenseType>
    private var mContext: Context? = null

//    private var listener: Listener? = null

    fun setContext(aContext: Context?) {
        mContext = aContext
    }

    override fun onAttachedToRecyclerView(@NonNull recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }


    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val lViewInflated: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_filter_expense, parent, false)
        return ViewHolder(lViewInflated)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val lAtividade: FilterExpenseType = mData[position]
        holder.itemView.tag = lAtividade
        bindData(holder, lAtividade)
        holder.itemView.setOnClickListener {
            holder.expandableLayout?.let {
                if(it.isExpanded) {
                    it.collapse()
                } else {
                    it.expand()
                }
            }
        }
    }

    private fun bindData(@NonNull aHolder: ViewHolder, aExpense: FilterExpenseType) {
        aHolder.description.text = aExpense.expenseType!!.descricao
        aHolder.totalValue.text = aExpense.totalValue().formatDoubleMoneyToString()
        aHolder.imageColor.setColorFilter(Color.parseColor(aExpense.expenseType!!.color), PorterDuff.Mode.SRC_IN)
        aHolder.rc?.adapter = FilterExpenseAdapter(this, aExpense.expenses)
    }

    fun clear() {
        mData.clear()
        notifyDataSetChanged()
    }

    interface Listener {
        fun onItemClick(aAtividade: FilterExpenseType?)
    }

    inner class ViewHolder(aItemView: View) : RecyclerView.ViewHolder(aItemView) {
        var description: TextView
        var totalValue: TextView
        var imageColor : ImageView
        var rc: RecyclerView? = null
        var expandableLayout: ExpandableLayout? = null

        init {
            rc = aItemView.findViewById(R.id.rc)
            expandableLayout = aItemView.findViewById(R.id.container_expadable)
            description = aItemView.findViewById(R.id.label_txt_description)
            imageColor = aItemView.findViewById(R.id.img_color)
            totalValue = aItemView.findViewById(R.id.txt_value)
        }
    }

    init {
        this.mData = aExpenseType
    }

    override fun onItemClick(aExpense: Expense?) {

    }
}