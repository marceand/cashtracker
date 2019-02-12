package com.marceme.cashtracker.statement

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import com.marceme.cashtracker.R
import com.marceme.cashtracker.expense.ExpenseAdapter
import com.marceme.cashtracker.model.Budget
import com.marceme.cashtracker.model.Expense
import com.marceme.cashtracker.textAsUSCurrency
import kotlinx.android.synthetic.main.activity_statement.*
import kotlinx.android.synthetic.main.balance_layout.*

const val BUDGET_ID_KEY = "com.marceme.cashtracker.budget_id"
class StatementActivity : AppCompatActivity(), StatementCallback{

    private lateinit var statementViewModel: StatementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statement)


        val expenseAdapter = ExpenseAdapter(this)
        val viewManager = LinearLayoutManager(this)

        recyclerview_expenses.apply {
            adapter = expenseAdapter
            layoutManager = viewManager
        }


        statementViewModel = ViewModelProviders.of(this).get(StatementViewModel::class.java)

        statementViewModel.budgetMediatorLiveData.observe(this,
                                        Observer { budget -> budget?.let {setBudget(it) }})

        statementViewModel.expensesMediatorLiveData.observe(this,
            Observer { expenses -> expenses?.let { expenseAdapter.addExpenses(it) } })

        val id = intent.getIntExtra(BUDGET_ID_KEY, 0)
        statementViewModel.loadBudget(id)
        statementViewModel.loadExpenses(id)

    }

    private fun setBudget(budget: Budget) {
        text_budget_description.text = budget.description
        text_budget_date.text = budget.date
        text_balance.textAsUSCurrency(budget.balance())
        text_total_budget.textAsUSCurrency(budget.amount)
        text_total_expense.textAsUSCurrency(budget.totalSpent)
    }

    override fun delete(expense: Expense) {
       statementViewModel.deleteExpense(expense)
    }
}
