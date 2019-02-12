package com.marceme.cashtracker.budget

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.marceme.cashtracker.*
import com.marceme.cashtracker.model.Budget
import com.marceme.cashtracker.statement.BUDGET_ID_KEY
import com.marceme.cashtracker.statement.StatementActivity
import kotlinx.android.synthetic.main.activity_budget.*

class BudgetActivity : AppCompatActivity(), BudgetCallback {

    private lateinit var budgetAdapter: BudgetAdapter
    private lateinit var budgetViewModel: BudgetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        budgetAdapter = BudgetAdapter(this)
        val viewManager = LinearLayoutManager(this)

        recyclerview_transaction.apply {
            adapter = budgetAdapter
            layoutManager = viewManager
        }

        favAddIncome.setOnClickListener {
            addBudget()
        }

        budgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel::class.java)
        budgetViewModel.allBudgets.observe(this, Observer { budgets ->
            budgets?.let {budgetAdapter.addBudgets(it)}})

    }

    private fun addBudget() {
        val intent = Intent(this, AddBudgetActivity::class.java)
        startActivityForResult(intent, ADD_BUDGET_CODE)
    }

    override fun showStatement(id: Int){
        val intent = Intent(this, StatementActivity::class.java)
        intent.putExtra(BUDGET_ID_KEY, id)
        startActivity(intent)
    }

    override fun addExpense(budget: Budget) {
        val intent = Intent(this, AddExpenseActivity::class.java)
        intent.putExtra(BUDGET_FOR_EXPENSE_KEY, budget)
        startActivity(intent)
    }

    override fun deleteBudget(budget: Budget) {
        budgetViewModel.deleteBudget(budget)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == ADD_BUDGET_CODE && resultCode == Activity.RESULT_OK){
            val budget = data?.getParcelableExtra<Budget>(BUDGET_KEY)
            budget?.let {
                budgetViewModel.saveBudget(it)
            }
        }
    }

}
