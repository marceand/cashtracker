package com.marceme.cashtracker.budget

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.marceme.cashtracker.database.BudgetRepository
import com.marceme.cashtracker.database.ExpenseRoomDatabase
import com.marceme.cashtracker.model.Budget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class BudgetViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: BudgetRepository
    val allBudgets: LiveData<List<Budget>>

    init {
        val budgetDao = ExpenseRoomDatabase.getDatabase(application).budgetDao()
        repository = BudgetRepository(budgetDao)
        allBudgets = repository.allBudget
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun saveBudget(budget: Budget) {
        scope.launch (Dispatchers.IO){
            try {
            repository.insert(budget)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteBudget(budget: Budget) {
        scope.launch (Dispatchers.IO){
            try {
                repository.delete(budget)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
