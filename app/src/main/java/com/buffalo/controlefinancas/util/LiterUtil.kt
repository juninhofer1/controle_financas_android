package com.buffalo.controlefinancas.util

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class LiterUtil @JvmOverloads constructor(val campo: EditText, divisor: Int = 100) : TextWatcher {
    private var current = ""
    private val divisor: Int

    //Pega a formatacao do sistema, se for brasil R$ se EUA US$
    private val nf: NumberFormat
    override fun onTextChanged(aCharSequence: CharSequence, start: Int, before: Int, count: Int) {
        if (aCharSequence.toString() != current) {
            try {
                val parsed = aCharSequence.toString().replace("[R$,.\\s]".toRegex(), "").toDouble()
                current = nf.format(if (divisor == 0) parsed else parsed / divisor).replace("R$", "")
                campo.setText(current)
                campo.setSelection(current.length)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        fun convertFormatStringForDouble(value: String, divisor: Int = 100): Double {
            val cleanString = value.replace("[R$,.\\s]".toRegex(), "")
            val parsed = cleanString.toDouble()
            return if (divisor == 0) parsed else parsed / divisor
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable) {}

    init {
        campo.inputType = InputType.TYPE_CLASS_NUMBER
        this.divisor = divisor
        nf = NumberFormat.getInstance(Locale("pt", "BR"))
        if (divisor > 0) {
            nf.minimumFractionDigits = BigDecimal(divisor / 10).precision()
            nf.maximumFractionDigits = BigDecimal(divisor / 10).precision()
        } else {
            nf.minimumFractionDigits = 0
            nf.maximumFractionDigits = 0
        }
    }
}