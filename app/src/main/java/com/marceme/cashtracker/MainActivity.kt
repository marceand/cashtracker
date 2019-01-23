package com.marceme.cashtracker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transactionAdapter = TransactionAdapter()
        val viewManager = LinearLayoutManager(this)

        recyclerview_transaction.apply {
            adapter = transactionAdapter
            layoutManager = viewManager
        }

        val transactionOne = Transaction("")
        val transactionTwo = Transaction("")
        val transactions = mutableListOf<Transaction>()
        transactions.add(transactionOne)
        transactions.add(transactionTwo)
        transactionAdapter.addTransactions(transactions)

        favAddIncome.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }
}
