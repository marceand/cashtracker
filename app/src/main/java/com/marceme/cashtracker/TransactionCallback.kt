package com.marceme.cashtracker

import com.marceme.cashtracker.model.Budget

interface TransactionCallback {
    fun showTransactionStatement()
    fun ShowAddExpense(budget: Budget)
}