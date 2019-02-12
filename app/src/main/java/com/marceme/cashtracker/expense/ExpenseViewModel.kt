package com.marceme.cashtracker.expense

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.marceme.cashtracker.database.BudgetRepository
import com.marceme.cashtracker.database.ExpenseRepository
import com.marceme.cashtracker.database.ExpenseRoomDatabase
import com.marceme.cashtracker.dateAsString
import com.marceme.cashtracker.model.Budget
import com.marceme.cashtracker.model.Expense
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*
import kotlin.coroutines.CoroutineContext

class ExpenseViewModel (application: Application) : AndroidViewModel(application){

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val expenseRepository: ExpenseRepository
    private val budgetRepository: BudgetRepository

    val isSavedState = MutableLiveData<Boolean>()


    init {
        val expenseDao = ExpenseRoomDatabase.getDatabase(application).expenseDao()
        val budgetDao = ExpenseRoomDatabase.getDatabase(application).budgetDao()
        expenseRepository = ExpenseRepository(expenseDao)
        budgetRepository = BudgetRepository(budgetDao)
    }


    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun saveExpense(description: String, spent:Long, budget: Budget) {
        val expense = Expense(description = description,
                            date = Date().dateAsString(),
                            spent = spent,
                            budgetId = budget.id)

        scope.launch (Dispatchers.Main){
            try {

                withContext(Dispatchers.IO) { expenseRepository.insert(expense) }

                val newBudget = budget.run { copy(totalSpent = totalSpent + spent) }
                withContext(Dispatchers.IO) { budgetRepository.update(newBudget) }

                isSavedState.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}