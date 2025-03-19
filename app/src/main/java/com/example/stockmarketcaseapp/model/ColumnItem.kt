package com.example.stockmarketcaseapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ColumnItem(
    val name: String,
    val key: String, //query
    var isSelected: Boolean = false
): Parcelable

fun ColumnItem.toFilter(): Filter? {
    return Filter.entries.find { it.filterKey == this.key }
}
