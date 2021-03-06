package com.marceme.expensetracker.database

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.marceme.expensetracker.model.Budget

class BudgetRepository(private val budgetDao: BudgetDao) {

    val allBudget: LiveData<List<Budget>> = budgetDao.getAllBudgets()

    @WorkerThread
    suspend fun insert(budget: Budget) {
        budgetDao.insert(budget)
    }

    @WorkerThread
    suspend fun update(budget: Budget) {
        budgetDao.update(budget)
    }

    @WorkerThread
    suspend fun delete(budget: Budget) {
        budgetDao.delete(budget);
    }

    fun getBudget(id:Int):LiveData<Budget> {
        return budgetDao.getBudget(id)
    }

}