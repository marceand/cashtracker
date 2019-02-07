package com.marceme.cashtracker

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.marceme.cashtracker.model.Budget
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TransactionCallback {

    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var budgetViewModel: BudgetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transactionAdapter = TransactionAdapter(this)
        val viewManager = LinearLayoutManager(this)

        recyclerview_transaction.apply {
            adapter = transactionAdapter
            layoutManager = viewManager
        }

        favAddIncome.setOnClickListener {
            showAddBudget()
        }

        budgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel::class.java)
        budgetViewModel.allBudgets.observe(this, Observer { budgets ->
            budgets?.let {transactionAdapter.addTransactions(it)}})

    }

    private fun showAddBudget() {
        val intent = Intent(this, AddBudgetActivity::class.java)
        startActivityForResult(intent, ADD_BUDGET_CODE)
    }

    override fun showTransactionStatement(){
        val intent = Intent(this, TransactionStatementActivity::class.java)
        startActivity(intent)
    }


    override fun ShowAddExpense() {
        val intent = Intent(this, AddExpenseActivity::class.java)
        startActivity(intent)
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
