package com.marceme.cashtracker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactions = mutableListOf<Transaction>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_layout, parent, false)

        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(p0: TransactionViewHolder, p1: Int) {
    }

    fun addTransactions(transactions: List<Transaction>){
        this.transactions.clear()
        this.transactions.addAll(transactions)
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(item: View) : RecyclerView.ViewHolder(item){

    }

}