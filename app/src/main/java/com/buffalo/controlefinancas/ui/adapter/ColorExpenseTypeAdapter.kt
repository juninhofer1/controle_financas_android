package com.buffalo.controlefinancas.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.buffalo.controlefinancas.R
import com.buffalo.controlefinancas.model.ColorExpense
import com.buffalo.controlefinancas.util.ColorUtil

class ColorExpenseTypeAdapter(aExpenseType: MutableList<ColorExpense>, listener : Listener) :
    RecyclerView.Adapter<ColorExpenseTypeAdapter.ViewHolder>() {

    private val mData: MutableList<ColorExpense>
    private var mContext: Context? = null
    private var listener: Listener? = null
    private var selectedPosition: Int = 0

    fun setContext(aContext: Context?) {
        mContext = aContext
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val lViewInflated: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_colors, parent, false)
        return ViewHolder(lViewInflated)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val lObject: ColorExpense = mData[position]
        holder.itemView.tag = lObject
        if(lObject.isItUsed!!) {
            holder.imageSelected.visibility = View.VISIBLE
            if (ColorUtil.dominatColor(lObject.hexadecimal!!) > ColorUtil.MEDIA_COLOR) {
                mContext?.let { holder.imageSelected.setColorFilter(ContextCompat.getColor(it, R.color.divider), PorterDuff.Mode.SRC_IN) }
            } else{
                mContext?.let { holder.imageSelected.setColorFilter(ContextCompat.getColor(it, R.color.white), PorterDuff.Mode.SRC_IN)}
            }
        } else {
            holder.imageSelected.visibility = View.INVISIBLE
        }
        bindData(holder, lObject)
        holder.itemView.setOnClickListener {
            updateItem(position)
            listener?.onItemClick(lObject)
        }
    }

    private fun updateItem(position: Int) {
        mData.forEach {
            it.isItUsed = false
        }
        mData[position].isItUsed = true
        notifyItemChanged(selectedPosition)
        notifyItemChanged(position)
        selectedPosition = position
    }

    private fun bindData(@NonNull aHolder: ViewHolder, aObject: ColorExpense) {
        aHolder.imageColor.setColorFilter(Color.parseColor(aObject.hexadecimal), PorterDuff.Mode.SRC_IN)

    }

    interface Listener {
        fun onItemClick(aAtividade: ColorExpense?)
    }

    class ViewHolder(aItemView: View) : RecyclerView.ViewHolder(aItemView) {
        var imageSelected: ImageView
        var imageColor : ImageView

        init {
            imageSelected = aItemView.findViewById(R.id.image_selected)
            imageColor = aItemView.findViewById(R.id.image_color)
        }
    }

    init {
        this.mData = aExpenseType
        this.listener = listener
    }
}