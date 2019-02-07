package com.marceme.cashtracker

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_add_expense.*

import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.android.synthetic.main.add_expense_content.*
import kotlinx.android.synthetic.main.add_budget_content.*

class TransactionStatementActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_statement)
    }
}
