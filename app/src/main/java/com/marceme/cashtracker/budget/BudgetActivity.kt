package com.marceme.cashtracker.budget

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.marceme.cashtracker.R
import com.marceme.cashtracker.addbudget.ADD_BUDGET_CODE
import com.marceme.cashtracker.addbudget.AddBudgetActivity
import com.marceme.cashtracker.addbudget.BUDGET_KEY
import com.marceme.cashtracker.addexpense.AddExpenseActivity
import com.marceme.cashtracker.addexpense.BUDGET_FOR_EXPENSE_KEY
import com.marceme.cashtracker.model.Budget
import com.marceme.cashtracker.statement.BUDGET_ID_KEY
import com.marceme.cashtracker.statement.StatementActivity
import kotlinx.android.synthetic.main.activity_budget.*
import kotlinx.android.synthetic.main.budget_content.*

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
            budgets?.let {showBudgets(it)}})

    }

    private fun showBudgets(it: List<Budget>) {
        if(it.isEmpty()) {
            empty_budget_message.visibility = View.VISIBLE
        }else{
            empty_budget_message.visibility = View.GONE
            budgetAdapter.addBudgets(it)
        }
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
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle(getString(R.string.delete_budget_title))
            setMessage(getString(R.string.delete_budget_message))
            setPositiveButton(R.string.ok
            ) { dialog, id ->
                budgetViewModel.deleteBudget(budget)
            }
            setNegativeButton(R.string.cancel, null)
        }
        builder.create().show()
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
