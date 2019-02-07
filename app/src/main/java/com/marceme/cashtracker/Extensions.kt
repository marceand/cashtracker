package com.marceme.cashtracker

import java.text.SimpleDateFormat
import java.util.*

fun Date.dateAsString(): String{
    val dateFormat = SimpleDateFormat("dd/MM/yy")
    return dateFormat.format(time)
}
