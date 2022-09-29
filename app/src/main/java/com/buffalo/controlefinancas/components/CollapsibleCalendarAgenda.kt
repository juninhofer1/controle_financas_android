package com.buffalo.controlefinancas.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.buffalo.controlefinancas.util.calendario.data.Event
import com.buffalo.controlefinancas.util.calendario.widget.CollapsibleCalendar
import com.buffalo.controlefinancas.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class CollapsibleCalendarAgenda : CollapsibleCalendar {
    constructor(context: Context) : super(context) {
        config(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        config(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        config(context)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    protected fun config(aContext: Context) {
        mLayoutRoot.setBackgroundColor(0)
        mLayoutRoot.background = ContextCompat.getDrawable(aContext, R.drawable.shape_accent_with_smooth_border)
        redraw()
    }

    public override fun reload() {
        super.reload()
        onMonthChange()
    }

    private fun onMonthChange() {
        try {
            val lStrDate = mTxtTitle.text.toString()
            var format = SimpleDateFormat("MMM yyyy", Locale.getDefault())
            var newDate = Date()
            format.parse(lStrDate)?.let {
                newDate = it
            }
            format = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            var date = format.format(newDate)
            date = date.substring(0, 1).uppercase(Locale.getDefault()) + date.substring(1)
            mTxtTitle.text = date
        } catch (aE: ParseException) {
            aE.printStackTrace()
        }
    }

    fun updateEvents(aEventList: List<Event?>?) {
        addEventTag(aEventList)
    }
}