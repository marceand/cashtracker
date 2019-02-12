package com.marceme.expensetracker.database

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.marceme.expensetracker.model.Expense

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpense: LiveData<List<Expense>> = expenseDao.getAllExpense()

    @WorkerThread
    suspend fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    @WorkerThread
    suspend fun update(expense: Expense) {
        expenseDao.update(expense)
    }

    @WorkerThread
    suspend fun delete(expense: Expense) {
        expenseDao.delete(expense);
    }

    fun getExpensesForBudget(id: Int): LiveData<List<Expense>> {
        return expenseDao.getExpenseForBudget(id)
    }
}