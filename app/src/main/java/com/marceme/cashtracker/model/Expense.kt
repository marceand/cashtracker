package com.marceme.cashtracker.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "expense_table",
        foreignKeys = [ForeignKey(entity = Budget::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("budgetId"),
        onDelete = CASCADE)]
)
data class Expense(@PrimaryKey(autoGenerate = true)
                           var expenseId: Int = 0,
                   val description: String,
                   val date: String,
                   val spent: Long,
                   val budgetId:Int)