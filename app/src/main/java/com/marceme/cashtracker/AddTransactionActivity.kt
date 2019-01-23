package com.marceme.cashtracker

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.android.synthetic.main.add_income_layout.*

class AddTransactionActivity : AppCompatActivity(){

    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        setSupportActionBar(toolbar
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        expenseAdapter = ExpenseAdapter()
        val viewManager = LinearLayoutManager(this)

//        expense_recyclerview.apply {
//            adapter = expenseAdapter
//            layoutManager = viewManager
//        }
//        add_expense_container.setOnClickListener {
//            showAddExpenseDialog()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_transaction_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

}
