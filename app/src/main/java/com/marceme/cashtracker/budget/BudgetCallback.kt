package com.marceme.cashtracker.budget

import com.marceme.cashtracker.model.Budget

interface BudgetCallback {
    fun showStatement(id: Int)
    fun addExpense(budget: Budget)
    fun deleteBudget(budget: Budget)
}