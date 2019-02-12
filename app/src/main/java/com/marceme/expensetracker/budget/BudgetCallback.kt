package com.marceme.expensetracker.budget

import com.marceme.expensetracker.model.Budget

interface BudgetCallback {
    fun showStatement(id: Int)
    fun addExpense(budget: Budget)
    fun deleteBudget(budget: Budget)
}