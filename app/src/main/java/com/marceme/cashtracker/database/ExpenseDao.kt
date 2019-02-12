package com.marceme.cashtracker.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.marceme.cashtracker.model.Expense

@Dao
interface ExpenseDao {

    @Query("SELECT * from expense_table ORDER BY date ASC")
    fun getAllExpense(): LiveData<List<Expense>>

    @Insert()
    fun insert(expense: Expense)

    @Update()
    fun update(expense: Expense)

    @Delete
    fun delete(expense: Expense)

    @Query("DELETE FROM expense_table")
    fun deleteAll()

    @Query("SELECT * FROM expense_table WHERE budgetId=:budgetId")
    fun getExpenseForBudget(budgetId: Int): LiveData<List<Expense>>
}