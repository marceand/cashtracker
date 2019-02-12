package com.marceme.cashtracker.addbudget

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.marceme.cashtracker.R
import com.marceme.cashtracker.dateAsString
import com.marceme.cashtracker.model.Budget
import kotlinx.android.synthetic.main.activity_add_budget.*
import kotlinx.android.synthetic.main.add_budget_content.*
import java.util.*

const val ADD_BUDGET_CODE = 111
const val BUDGET_KEY = "com.marceme.cashtracker.budgetLiveData"
class AddBudgetActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_budget)
        setSupportActionBar(toolbar_add_budget)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        button_save_budget.setOnClickListener {
            saveBudget()
        }
    }

    private fun saveBudget() {
        val description = edit_budget_description.text.toString()
        val amount = edit_budget_amount.rawValue


        if(description.isEmpty()){
            edit_budget_description.error = getString(R.string.error_empty_income_description)
            return
        }

        if(amount == 0L){
            edit_budget_amount.error = getString(R.string.error_empty_total_income)
            return
        }

        val budget = Budget(description = description, amount = amount, date = Date().dateAsString())

        val intent = Intent()
        intent.putExtra(BUDGET_KEY, budget)
        setResult(RESULT_OK, intent)
        finish()
    }

}
