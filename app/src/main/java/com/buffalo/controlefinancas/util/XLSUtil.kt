package com.buffalo.controlefinancas.util

import com.buffalo.controlefinancas.model.Expense
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress


object XLSUtil {

    fun createCellsFuel(lastRow: Int, workbook: Workbook, sheet: Sheet, fuelList:  MutableList<Expense>, totalExpense: Double) : Int {
        val row: Row = sheet.createRow(lastRow)

        sheet.setColumnWidth(0, 6000)
        sheet.setColumnWidth(1, 6000)
        sheet.setColumnWidth(2, 6000)
        sheet.setColumnWidth(3, 6000)
        sheet.setColumnWidth(4, 6000)
        sheet.setColumnWidth(5, 6000)
        sheet.setColumnWidth(6, 6000)
        sheet.setColumnWidth(7, 6000)

        val cellStyle = workbook.createCellStyle()
        cellStyle.fillForegroundColor = HSSFColor.AQUA.index
        cellStyle.fillPattern = HSSFCellStyle.SOLID_FOREGROUND
        cellStyle.alignment = CellStyle.ALIGN_CENTER

        row.createHeaderCell(0, "Localidade", cellStyle)
        row.createHeaderCell(1, "Tipo Despesa", cellStyle)
        row.createHeaderCell(2, "Descriminação", cellStyle)
        row.createHeaderCell(3, "NF", cellStyle)
        row.createHeaderCell(4, "Data", cellStyle)
        row.createHeaderCell(5, "Km", cellStyle)
        row.createHeaderCell(6, "Litro", cellStyle)
        row.createHeaderCell(7, "Valor", cellStyle)

        var count = lastRow + 1
        for (item: Expense in fuelList) {
            val row2: Row = sheet.createRow(count)
            row2.createHeaderCell(0, "${item.city} - ${item.state}", cellStyleCenter(workbook))
            row2.createHeaderCell(1, "${item.expenseType!!.descricao}", cellStyleLeft(workbook))
            row2.createHeaderCell(2, "${item.descricao}", cellStyleLeft(workbook))
            row2.createHeaderCell(3, "${item.numberTicket}", cellStyleRight(workbook))
            row2.createHeaderCell(4, FormatUtil.dateFormat(item.dataRegiter, "dd/MM/yyyy HH:mm:ss"), cellStyleCenter(workbook))
            row2.createHeaderCell(5, item.km.toString(), cellStyleRight(workbook))
            item.literage?.formatDoubleLiterToString()?.let { row2.createHeaderCell(6, it, cellStyleRight(workbook)) }
            row2.createHeaderCell(7, item.amount!!.formatDoubleMoneyToString(), cellStyleRight(workbook))
            count += 1
        }

        val rowTotalFuel: Row = sheet.createRow(count)
        sheet.addMergedRegion(CellRangeAddress(count, count, 0, 5))
        rowTotalFuel.createHeaderCell(0, "TOTAL ${fuelList[0].expenseType!!.descricao!!.uppercase()}", cellStyleRightBold(workbook))
        rowTotalFuel.createHeaderCell(6, fuelList.sumOf { it.literage!! }.formatDoubleLiterToString(), cellStyleRightBold(workbook))
        rowTotalFuel.createHeaderCell(7, totalExpense.formatDoubleMoneyToString(), cellStyleRightBold(workbook))

        return count
    }


    fun createCellsNormal(lastRow: Int, workbook: Workbook, sheet: Sheet, fuelList:  MutableList<Expense>, totalExpense: Double) : Int {
        val row: Row = sheet.createRow(lastRow)

        sheet.setColumnWidth(0, 6000)
        sheet.setColumnWidth(1, 6000)
        sheet.setColumnWidth(2, 6000)
        sheet.setColumnWidth(3, 6000)
        sheet.setColumnWidth(4, 6000)
        sheet.setColumnWidth(5, 6000)

        val cellStyle = workbook.createCellStyle()
        cellStyle.fillForegroundColor = HSSFColor.AQUA.index
        cellStyle.fillPattern = HSSFCellStyle.SOLID_FOREGROUND
        cellStyle.alignment = CellStyle.ALIGN_CENTER

        row.createHeaderCell(0, "Localidade", cellStyle)
        row.createHeaderCell(1, "Tipo Despesa", cellStyle)
        row.createHeaderCell(2, "Descriminação", cellStyle)
        row.createHeaderCell(3, "NF", cellStyle)
        row.createHeaderCell(4, "Data", cellStyle)
        row.createHeaderCell(5, "Valor", cellStyle)

        var count = lastRow + 1
        for (item: Expense in fuelList) {
            val row2: Row = sheet.createRow(count)
            row2.createHeaderCell(0, "${item.city} - ${item.state}", cellStyleCenter(workbook))
            row2.createHeaderCell(1, "${item.expenseType!!.descricao}", cellStyleLeft(workbook))
            row2.createHeaderCell(2, "${item.descricao}", cellStyleLeft(workbook))
            row2.createHeaderCell(3, "${item.numberTicket}", cellStyleRight(workbook))
            row2.createHeaderCell(4, FormatUtil.dateFormat(item.dataRegiter, "dd/MM/yyyy HH:mm:ss"), cellStyleCenter(workbook))
            row2.createHeaderCell(5, item.amount!!.formatDoubleMoneyToString(), cellStyleRight(workbook))
            count += 1
        }

        val rowTotalFuel: Row = sheet.createRow(count)
        sheet.addMergedRegion(CellRangeAddress(count, count, 0, 4))
        rowTotalFuel.createHeaderCell(0, "TOTAL ${fuelList[0].expenseType!!.descricao!!.uppercase()}", cellStyleRightBold(workbook))
        rowTotalFuel.createHeaderCell(5, totalExpense.formatDoubleMoneyToString(), cellStyleRightBold(workbook))

        return count
    }

    fun cellStyleCenter(workbook: Workbook) : CellStyle {
        val cellStyle = workbook.createCellStyle()
        cellStyle.alignment = CellStyle.ALIGN_CENTER
        return cellStyle
    }

    fun cellStyleLeft(workbook: Workbook) : CellStyle {
        val cellStyle = workbook.createCellStyle()
        cellStyle.alignment = CellStyle.ALIGN_LEFT
        return cellStyle
    }

    fun cellStyleRight(workbook: Workbook) : CellStyle {
        val cellStyle = workbook.createCellStyle()
        cellStyle.alignment = CellStyle.ALIGN_RIGHT
        return cellStyle
    }

    fun cellStyleRightBold(workbook: Workbook) : CellStyle {
        val cellStyle = workbook.createCellStyle()
        cellStyle.alignment = CellStyle.ALIGN_RIGHT
        cellStyle.setFont(createFontBold(workbook))
        return cellStyle
    }

    fun createFontBold(workbook: Workbook) : Font {
        val font: Font = workbook.createFont()
        font.boldweight = 20.toShort()
        font.italic = false
        return font
    }
}

fun Row.createHeaderCell(column : Int, cellValue : String, cellStyle: CellStyle?) {
    val cell = this.createCell(column)
    cell.setCellValue(cellValue)
    cell.cellStyle = cellStyle
}