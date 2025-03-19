package com.example.stockmarketcaseapp.model

data class ColumnItem(
    val name: String,
    val key: String //query
)

fun ColumnItem.toFilter(): Filter? {
    return Filter.entries.find { it.filterKey == this.key }
}
