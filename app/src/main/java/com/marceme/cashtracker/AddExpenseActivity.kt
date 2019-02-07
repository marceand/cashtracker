package com.marceme.cashtracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import kotlinx.android.synthetic.main.activity_add_expense.*

class AddExpenseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        setSupportActionBar(toolbar_add_expense)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
