package com.marceme.expensetracker.statement

import com.marceme.expensetracker.model.Expense

interface StatementCallback {
    fun delete(expense: Expense)
}