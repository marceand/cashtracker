package com.marceme.cashtracker

import android.widget.TextView
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.dateAsString(): String{
    val dateFormat = SimpleDateFormat("dd/MM/yy")
    return dateFormat.format(time)
}

fun TextView.textAsUSCurrency(amountInCents: Long) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    text = numberFormat.format(amountInCents / 100.0);
}
