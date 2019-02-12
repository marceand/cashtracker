package com.marceme.expensetracker.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.marceme.expensetracker.model.Budget
import com.marceme.expensetracker.model.Expense

@Database(entities = [Budget::class, Expense::class], version = 1)
public abstract class ExpenseRoomDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseRoomDatabase? = null

        fun getDatabase(context: Context): ExpenseRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                    ExpenseRoomDatabase::class.java,
                        "budget_expense_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}