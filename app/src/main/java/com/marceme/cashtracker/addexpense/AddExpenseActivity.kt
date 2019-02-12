package com.marceme.cashtracker.addexpense

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import com.marceme.cashtracker.R
import com.marceme.cashtracker.expense.ExpenseViewModel
import com.marceme.cashtracker.model.Budget
import kotlinx.android.synthetic.main.activity_add_expense.*
import kotlinx.android.synthetic.main.add_expense_content.*

const val BUDGET_FOR_EXPENSE_KEY = "com.marceme.cashtracker.budget_for_expense"
class AddExpenseActivity : AppCompatActivity(){

    private lateinit var expenseViewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        setSupportActionBar(toolbar_add_expense)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel::class.java)

        button_save_expense.setOnClickListener {
            saveExpensive()
        }

        expenseViewModel.isSaveState.observe(this, Observer {
            if(it == true){
                finish()
            }
        })
    }

    private fun saveExpensive() {
        val description = edit_expense_description.text.toString()
        val spent = edit_expense_total.rawValue


        if(description.isEmpty()){
            edit_expense_description.error = getString(R.string.error_expense_description)
            return
        }

        if(spent == 0L){
            edit_expense_total.error = getString(R.string.error_expense_total)
            return
        }
        val budget = intent.getParcelableExtra<Budget>(BUDGET_FOR_EXPENSE_KEY)
        expenseViewModel.saveExpense(description, spent, budget)
    }
}
