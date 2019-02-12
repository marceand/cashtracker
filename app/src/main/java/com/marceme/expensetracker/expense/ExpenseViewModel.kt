package com.marceme.expensetracker.expense

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.marceme.expensetracker.database.BudgetRepository
import com.marceme.expensetracker.database.ExpenseRepository
import com.marceme.expensetracker.database.ExpenseRoomDatabase
import com.marceme.expensetracker.dateAsString
import com.marceme.expensetracker.model.Budget
import com.marceme.expensetracker.model.Expense
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class ExpenseViewModel (application: Application) : AndroidViewModel(application){

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val expenseRepository: ExpenseRepository
    private val budgetRepository: BudgetRepository

    val isSaveState = MutableLiveData<Boolean>()


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

                isSaveState.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}