package com.buffalo.controlefinancas.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun dataFormat(aFormato: String, aData: Date): String {
        return data(aFormato, aData)
    }

    fun data(aData: Date): String {
        return data("dd/MM/yyyy HH:mm:ss", aData)
    }

    fun data(aFormato: String, aDate: String?): String {
        val parse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(aDate)
        return data(aFormato, parse)
    }

    fun dataPayment(aFormato: String, aDate: String?): String {
        if(aDate!!.contains("/")) {
            return dataPaymentII(aFormato, aDate)
        }
        val parse = SimpleDateFormat("yyyy-MM-dd").parse(aDate)
        return data(aFormato, parse)
    }

    fun dataPaymentToDate(aFormato: String, aDate: String?): Date {
        if(aDate!!.contains("/")) {
            return SimpleDateFormat("dd/MM/yyyy").parse(aDate)
        }
        val parse = SimpleDateFormat("yyyy-MM-dd").parse(aDate)
        return parse
    }

    fun dataPaymentII(aFormato: String, aDate: String?): String {
        val parse = SimpleDateFormat("dd/MM/yyyy").parse(aDate)
        return data(aFormato, parse)
    }

    fun dataToDate(formato : String, aData: String): Date {
        return SimpleDateFormat(formato).parse(aData)
    }

    fun data(aFormato: String, aDate: String?, aFormatoString : String?): String {
        val parse = SimpleDateFormat(aFormatoString).parse(aDate)
        return data(aFormato, parse)
    }

    fun data(aData: String): Date {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(aData)
    }

    fun data(aData: String, formarto : String): Date {
        return SimpleDateFormat(formarto).parse(aData)
    }

    fun data(aFormato: String, aData: Date?): String {
        return if (aData != null) SimpleDateFormat(aFormato).format(aData) else ""
    }

    fun data(aData: Date, aFormato: String): String {
        return data(aFormato, aData)
    }

    fun dataAtual(aFormato: String): String {
        return SimpleDateFormat(aFormato).format(Date())
    }

    fun addDaysAtualDate(aFormato: String, daysAdd : Int): String {
        var calendar : Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, daysAdd)
        return SimpleDateFormat(aFormato).format(calendar.time)
    }

    fun getLastDayMonth(aCurrentMonth: Calendar): Calendar {
        val lMaxCalendar = aCurrentMonth.clone() as Calendar
        lMaxCalendar.set(
            Calendar.DAY_OF_MONTH,
            aCurrentMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
        )
        lMaxCalendar.set(Calendar.HOUR_OF_DAY, 23)
        lMaxCalendar.set(Calendar.MINUTE, 59)
        lMaxCalendar.set(Calendar.SECOND, 59)
        lMaxCalendar.set(Calendar.MILLISECOND, 999)
        return lMaxCalendar
    }

    fun getFirstDayMonth(aCurrentMonth: Calendar): Calendar {
        val lMinCalendar = aCurrentMonth.clone() as Calendar
        lMinCalendar.set(
            Calendar.DAY_OF_MONTH,
            aCurrentMonth.getActualMinimum(Calendar.DAY_OF_MONTH)
        )
        lMinCalendar.set(Calendar.HOUR_OF_DAY, 0)
        lMinCalendar.set(Calendar.MINUTE, 0)
        lMinCalendar.set(Calendar.SECOND, 0)
        lMinCalendar.set(Calendar.MILLISECOND, 0)
        return lMinCalendar
    }
}