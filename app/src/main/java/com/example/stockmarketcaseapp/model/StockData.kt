package com.example.stockmarketcaseapp.model

data class StockData(
    val _id: String,
    val DateTime: Long,
    val Code: String,
    val Precision: Int,
    val Last: Double
)
