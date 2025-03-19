package com.example.stockmarketcaseapp.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

data class StockData(
    @SerializedName("_id") val id: String,
    @SerializedName("DateTime") val dateTime: Long,
    @SerializedName("Code") val code: String,
    @SerializedName("Precision") val precision: Int?,
    @SerializedName("Last") val son: Double?,
    @SerializedName("DailyChangePercent") val percentFark: Double?,
    @SerializedName("DailyChange") val fark: Double?,
    @SerializedName("Low") val dusuk: Double?,
    @SerializedName("High") val yuksek: Double?,
    @SerializedName("Bid") val alis: Double?,
    @SerializedName("Ask") val satis: Double?,
    @SerializedName("PreviousClose") val oGKap: Double?,
    @SerializedName("UpperLimit") val tavan: Double?,
    @SerializedName("LowerLimit") val taban: Double?,
    @SerializedName("WOWeeklyChangePercent") val haftalikPercentFark: Double?,
    @SerializedName("MOMonthlyChangePercent") val aylikPercentFark: Double?,
    @SerializedName("YOYearlyChangePercent") val yillikPercentFark: Double?,
) {
    fun getFormattedDateTime(): String {
        val dateFormat = SimpleDateFormat("HH.mm.ss", Locale.getDefault())
        return dateFormat.format(Date(dateTime))
    }
}

fun StockData.mapToUiModel(selectedFilters: List<Filter>): StockDataUiModel {
    require(selectedFilters.size == 2) { "Exactly 2 filters must be selected." }

    val formattedTime = getFormattedDateTime()

    val column1Filter = selectedFilters[0]
    val column2Filter = selectedFilters[1]

    val column1Value = getStockDataValue(column1Filter)
    val column2Value = getStockDataValue(column2Filter)

    return StockDataUiModel(
        id = id,
        time = formattedTime,
        code = code,
        lastDouble = son,
        valueChange = getValueChange(percentFark),
        column1Text = "${column1Filter.filterName}: $column1Value",
        column2Text = "${column2Filter.filterName}: $column2Value"
    )
}

fun StockData.getStockDataValue(filter: Filter): String {
    return when (filter) {
        Filter.SON -> son?.toString()
        Filter.PERCENT_CHANGE -> percentFark?.toString()
        Filter.CHANGE -> fark?.toString()
        Filter.LOW -> dusuk?.toString()
        Filter.HIGH -> yuksek?.toString()
        Filter.BID -> alis?.toString()
        Filter.ASK -> satis?.toString()
        Filter.PREVIOUS_CLOSE -> oGKap?.toString()
        Filter.UPPER_LIMIT -> tavan?.toString()
        Filter.LOWER_LIMIT -> taban?.toString()
        Filter.WEEKLY_PERCENT_CHANGE -> haftalikPercentFark?.toString()
        Filter.MONTHLY_PERCENT_CHANGE -> aylikPercentFark?.toString()
        Filter.YEARLY_PERCENT_CHANGE -> yillikPercentFark?.toString()
    } ?: "N/A"
}

fun getValueChange(percentFark: Double?): ValueChange {
    return when {
        percentFark == null -> ValueChange.SAME
        percentFark > 0 -> ValueChange.INCREASED
        percentFark < 0 -> ValueChange.DECREASED
        else -> ValueChange.SAME
    }
}
