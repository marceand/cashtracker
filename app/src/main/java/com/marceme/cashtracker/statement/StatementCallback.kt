package com.marceme.cashtracker.statement

import com.marceme.cashtracker.model.Expense

interface StatementCallback {
    fun delete(expense: Expense)
}