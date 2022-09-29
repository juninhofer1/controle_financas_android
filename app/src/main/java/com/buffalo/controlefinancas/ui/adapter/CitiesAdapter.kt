package com.buffalo.controlefinancas.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import com.buffalo.controlefinancas.R

class CitiesAdapter(aExpenseType: MutableList<String>, listener : Listener) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private val mData: MutableList<String>
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
            LayoutInflater.from(parent.context).inflate(R.layout.row_state, parent, false)
        return ViewHolder(lViewInflated)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val lAtividade: String = mData[position]
        holder.itemView.tag = lAtividade
        bindData(holder, lAtividade)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(lAtividade)
        }
    }

    private fun bindData(@NonNull aHolder: ViewHolder, aObject: String) {
        aHolder.description.text = aObject
    }

    fun clear() {
        mData.clear()
        notifyDataSetChanged()
    }

    interface Listener {
        fun onItemClick(aAtividade: String?)
    }

    inner class ViewHolder(aItemView: View) : RecyclerView.ViewHolder(aItemView) {
        var description: TextView
        init {
            description = aItemView.findViewById(R.id.description)
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