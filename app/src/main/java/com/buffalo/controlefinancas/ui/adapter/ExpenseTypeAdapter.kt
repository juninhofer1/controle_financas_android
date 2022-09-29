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
import com.buffalo.controlefinancas.model.ExpenseType

class ExpenseTypeAdapter(aExpenseType: MutableList<ExpenseType>, listener : Listener) :
    RecyclerView.Adapter<ExpenseTypeAdapter.ViewHolder>() {

    private val mData: MutableList<ExpenseType>
    private var mContext: Context? = null
    private var listener: Listener? = null

    fun setContext(aContext: Context?) {
        mContext = aContext
    }

    override fun onAttachedToRecyclerView(@NonNull recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }


    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val lViewInflated: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_expense_type, parent, false)
        return ViewHolder(lViewInflated)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val lAtividade: ExpenseType = mData[position]
        holder.itemView.tag = lAtividade
        bindData(holder, lAtividade)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(lAtividade)
        }
    }

    private fun bindData(@NonNull aHolder: ViewHolder, aExpense: ExpenseType) {
        aHolder.description.text = aExpense.descricao
        aHolder.imageColor.setColorFilter(Color.parseColor(aExpense.color), PorterDuff.Mode.SRC_IN)
    }

    fun clear() {
        mData.clear()
        notifyDataSetChanged()
    }

    interface Listener {
        fun onItemClick(aAtividade: ExpenseType?)
    }

    inner class ViewHolder(aItemView: View) : RecyclerView.ViewHolder(aItemView) {
        var description: TextView
        var imageColor : ImageView

        init {
            description = aItemView.findViewById(R.id.description)
            imageColor = aItemView.findViewById(R.id.image_color)
        }
    }

    companion object {
        private const val TAG = "ExpenseTypeAdapter"
    }

    init {
        this.mData = aExpenseType
        this.listener = listener
    }
}