package com.marceme.cashtracker.statement

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import com.marceme.cashtracker.database.BudgetRepository
import com.marceme.cashtracker.database.ExpenseRepository
import com.marceme.cashtracker.database.ExpenseRoomDatabase
import com.marceme.cashtracker.model.Budget
import com.marceme.cashtracker.model.Expense
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class StatementViewModel (application: Application) : AndroidViewModel(application){

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val expenseRepository: ExpenseRepository
    private val budgetRepository: BudgetRepository

    val budgetMediatorLiveData = MediatorLiveData<Budget>()
    val expensesMediatorLiveData = MediatorLiveData<List<Expense>>()

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

    fun loadBudget(id: Int) {
        scope.launch (Dispatchers.Main){
            try {
                val budgetLiveData  = withContext(Dispatchers.IO) { budgetRepository.getBudget(id) }
                budgetMediatorLiveData.addSource(budgetLiveData){ result -> budgetMediatorLiveData.value = result}
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadExpenses(id: Int) {
        scope.launch (Dispatchers.Main){
            try {
                val expensesLiveData = withContext(Dispatchers.IO) { expenseRepository.getExpensesForBudget(id) }
                expensesMediatorLiveData.addSource(expensesLiveData){ result -> expensesMediatorLiveData.value = result}
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteExpense(expense: Expense) {
        scope.launch (Dispatchers.Main){
            try {
                withContext(Dispatchers.IO) { expenseRepository.delete(expense) }
                updateBudget(expense.spent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun updateBudget(spent: Long) {
        val newBudget = budgetMediatorLiveData.value?.run { copy(totalSpent = totalSpent - spent) }
        newBudget?.let { withContext(Dispatchers.IO) { budgetRepository.update(it) } }
    }
}


// nicolas gift 50
// hair cut 17
// chocolate hersey 1