package com.buffalo.controlefinancas.util

import com.buffalo.controlefinancas.model.ExpenseType
import com.example.controlefinancas.database.ExpenseTypeDAO

object ExpenseUtil {
    fun createExpenseDefault() {
        ExpenseTypeDAO.update(ExpenseType(1, "Alimentação", "#9FE2BF"))
        ExpenseTypeDAO.update(ExpenseType(2, "Combustível", "#FF7F50"))
        ExpenseTypeDAO.update(ExpenseType(3, "Hospedagem",  "#CCCCFF"))
        ExpenseTypeDAO.update(ExpenseType(4, "Transporte", "#f54275"))
        ExpenseTypeDAO.update(ExpenseType(5, "Aluguel", "#42f54e"))
    }
}