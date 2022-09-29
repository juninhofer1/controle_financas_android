package com.buffalo.controlefinancas.model

class FilterExpenseType {

    var expenseType : ExpenseType? = null
    var expenses:  MutableList<Expense> = mutableListOf()

    fun totalValue() : Double {
        return expenses.sumOf { it.amount!! }
    }

    fun addExpense(value : Expense) {
        expenses.add(value)
    }
}