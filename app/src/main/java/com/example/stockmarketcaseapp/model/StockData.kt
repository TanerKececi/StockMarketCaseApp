package com.example.stockmarketcaseapp.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class StockData(
    val _id: String,
    val DateTime: Long,
    val Code: String,
    val Precision: Int,
    val Last: Double
) {
    fun getFormattedDateTime(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date(DateTime))
    }
}
