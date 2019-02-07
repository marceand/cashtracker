package com.marceme.cashtracker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marceme.cashtracker.model.Budget
import kotlinx.android.synthetic.main.activity_transaction_statement.view.*
import kotlinx.android.synthetic.main.transaction_layout.view.*

class TransactionAdapter(val transactionCallback: TransactionCallback) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactions = mutableListOf<Budget>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_layout, parent, false)

        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(viewHolder: TransactionViewHolder, position: Int) {
        viewHolder.bind(transactions[position])
    }

    fun addTransactions(budgets: List<Budget>){
        this.transactions.clear()
        this.transactions.addAll(budgets)
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(val item: View) : RecyclerView.ViewHolder(item){

        fun bind(budget: Budget) {
            item.budget_description_text.text = budget.description
            item.budget_date_text.text = budget.date

            item.text_add_expense.setOnClickListener {
                transactionCallback.ShowAddExpense()
            }

            item.setOnLongClickListener{
                transactionCallback.showTransactionStatement()
                true
            }
        }

    }

}