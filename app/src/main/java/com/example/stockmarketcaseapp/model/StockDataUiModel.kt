package com.example.stockmarketcaseapp.model

data class StockDataUiModel(
    val id: String,
    val time: String,
    val code: String,
    val definition: String,
    val lastDouble: Double?,
    val valueChange: ValueChange,
    val column1Text: String?,
    val column2Text: String?,
    val textColor: Int?
)
