package com.marceme.cashtracker

import com.marceme.cashtracker.model.Expense

interface AddExpenseCallback {
    fun addExpense(expense: Expense)
}