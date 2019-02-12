package com.marceme.expensetracker.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "budget_table")
@Parcelize
data class Budget(@PrimaryKey(autoGenerate = true)
                              var id: Int = 0,
                              val description: String,
                              val amount: Long = 0,
                              val date: String,
                              val totalSpent: Long = 0): Parcelable{
    fun balance(): Long = amount - totalSpent
}
