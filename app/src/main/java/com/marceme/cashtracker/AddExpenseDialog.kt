package com.marceme.cashtracker

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.EditText
import com.blackcat.currencyedittext.CurrencyEditText
import kotlinx.android.synthetic.main.fragment_add_expense.view.*
import com.marceme.cashtracker.R.id.editText



class AddExpenseDialog : DialogFragment() {

    private var addExpenseCallback: AddExpenseCallback ? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        addExpenseCallback = context as? AddExpenseCallback
    }

    override fun onDetach() {
        super.onDetach()
        addExpenseCallback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.fragment_add_expense, null);

        val expenseNameEditText = view.expense_name_edit_text
        val expenseTotalEditText: CurrencyEditText = view.expense_total_edit_text
        alertDialogBuilder.setView(view)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setTitle("Add Expense")
        alertDialogBuilder.setNegativeButton("Cancel", null)
        alertDialogBuilder.setPositiveButton("Save", { dialog, button ->  save(expenseNameEditText, expenseTotalEditText) })
        return alertDialogBuilder.create()
    }

    private fun save(expenseNameEditText: EditText, expenseTotalEditText: CurrencyEditText) {
        val expenseName = expenseNameEditText.text.toString()
        val expenseTotal = expenseTotalEditText.text.toString()

        if(expenseName.isNotEmpty() && expenseTotal.isNotEmpty()){
            addExpenseCallback?.addExpense(Expense(expenseName, expenseTotalEditText.rawValue))
        }
    }

}