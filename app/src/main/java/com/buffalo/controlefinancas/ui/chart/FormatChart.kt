package com.buffalo.controlefinancas.ui.chart

import com.buffalo.controlefinancas.util.formatDoublePecentToString
import com.github.mikephil.charting.formatter.ValueFormatter

class FormatChart: ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return value.toDouble().formatDoublePecentToString()
    }
}

