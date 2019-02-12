package com.marceme.cashtracker.budget

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marceme.cashtracker.R
import com.marceme.cashtracker.model.Budget
import com.marceme.cashtracker.textAsUSCurrency
import kotlinx.android.synthetic.main.balance_layout.view.*
import kotlinx.android.synthetic.main.budget_row_layout.view.*


class BudgetAdapter(val budgetCallback: BudgetCallback) : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    private var budgets = mutableListOf<Budget>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BudgetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.budget_row_layout, parent, false)

        return BudgetViewHolder(view)
    }

    override fun getItemCount(): Int = budgets.size

    override fun onBindViewHolder(viewHolder: BudgetViewHolder, position: Int) {
        viewHolder.bind(budgets[position])
    }

    fun addBudgets(budgets: List<Budget>){
        this.budgets.clear()
        this.budgets.addAll(budgets)
        notifyDataSetChanged()
    }

    inner class BudgetViewHolder(val item: View) : RecyclerView.ViewHolder(item){

        fun bind(budget: Budget) {
            item.budget_description_text.text = budget.description
            item.budget_date_text.text = budget.date
            item.text_balance.textAsUSCurrency(budget.balance())
            item.text_total_budget.textAsUSCurrency(budget.amount)
            item.text_total_expense.textAsUSCurrency(budget.totalSpent)
            item.image_more_option.setOnClickListener {
                val popup = PopupMenu(item.context, item.image_more_option)
                popup.inflate(R.menu.budget_row_menu)
                popup.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.edit ->{
                            true
                              }
                        R.id.delete ->{
                            budgetCallback.deleteBudget(budget)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }

            item.text_add_expense.setOnClickListener {
                budgetCallback.addExpense(budget)
            }

            item.setOnLongClickListener{
                budgetCallback.showStatement(budget.id)
                true
            }
        }

    }

}