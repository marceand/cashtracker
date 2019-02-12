package com.marceme.cashtracker.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.marceme.cashtracker.model.Budget

@Dao
interface BudgetDao {

    @Query("SELECT * from budget_table ORDER BY date ASC")
    fun getAllBudgets(): LiveData<List<Budget>>

    @Insert()
    fun insert(transaction: Budget)

    @Update()
    fun update(transaction: Budget)

    @Delete
    fun delete(transaction: Budget)

    @Query("DELETE FROM budget_table")
    fun deleteAll()

    @Query("SELECT * FROM budget_table WHERE id = :id")
    fun getBudget(id: Int): LiveData<Budget>
}