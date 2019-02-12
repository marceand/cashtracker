package com.marceme.expensetracker.statement

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.marceme.expensetracker.R
import com.marceme.expensetracker.model.Budget
import com.marceme.expensetracker.model.Expense
import com.marceme.expensetracker.textAsUSCurrency
import kotlinx.android.synthetic.main.activity_statement.*
import kotlinx.android.synthetic.main.balance_layout.*

const val BUDGET_ID_KEY = "com.marceme.expensetracker.intent_budget_id"
class StatementActivity : AppCompatActivity(), StatementCallback{

    private lateinit var statementViewModel: StatementViewModel
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statement)


        expenseAdapter = ExpenseAdapter(this)
        val viewManager = LinearLayoutManager(this)

        recyclerview_expenses.apply {
            adapter = expenseAdapter
            layoutManager = viewManager
            addItemDecoration(DividerItemDecoration(this@StatementActivity, DividerItemDecoration.VERTICAL))
        }


        statementViewModel = ViewModelProviders.of(this).get(StatementViewModel::class.java)

        statementViewModel.budgetMediatorLiveData.observe(this,
                                            Observer { budget -> budget?.let {showBudget(it) }})

        statementViewModel.expensesMediatorLiveData.observe(this,
            Observer { expenses -> expenses?.let { showExpenses(it)} })

        val id = intent.getIntExtra(BUDGET_ID_KEY, 0)
        statementViewModel.loadBudget(id)
        statementViewModel.loadExpenses(id)

    }

    private fun showExpenses(expenses: List<Expense>) {
        if(expenses.isNotEmpty()) {
            empty_expenses.visibility = View.GONE
        }else{
            empty_expenses.visibility = View.VISIBLE
        }
        expenseAdapter.addExpenses(expenses)
    }

    private fun showBudget(budget: Budget) {
        text_budget_description.text = budget.description
        text_budget_date.text = budget.date
        text_balance.textAsUSCurrency(budget.balance())
        text_total_budget.textAsUSCurrency(budget.amount)
        text_total_expense.textAsUSCurrency(budget.totalSpent)
    }

    override fun delete(expense: Expense) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle(R.string.delete_expense_title)
            setMessage(R.string.delete_expense_message)
            setPositiveButton(R.string.ok
            ) { dialog, id ->
                statementViewModel.deleteExpense(expense)
            }
            setNegativeButton(R.string.cancel, null)
        }
        builder.create().show()
    }
}
