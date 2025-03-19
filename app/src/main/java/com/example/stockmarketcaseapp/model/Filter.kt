package com.example.stockmarketcaseapp.model

enum class Filter (val filterName: String, val filterKey: String) {
    SON("Son", "Last"),
    PERCENT_CHANGE("%Fark", "DailyChangePercent"),
    CHANGE("Fark", "DailyChange"),
    LOW("Düşük", "Low"),
    HIGH("Yüksek", "High"),
    BID("Alış", "Bid"),
    ASK("Satış", "Ask"),
    PREVIOUS_CLOSE("Ö.G.Kap", "PreviousClose"),
    UPPER_LIMIT("Tavan", "UpperLimit"),
    LOWER_LIMIT("Taban", "LowerLimit"),
    WEEKLY_PERCENT_CHANGE("Haftalık %Fark", "WOWeeklyChangePercent"),
    MONTHLY_PERCENT_CHANGE("Aylık %Fark", "MOMonthlyChangePercent"),
    YEARLY_PERCENT_CHANGE("Yıllık %Fark", "YOYearlyChangePercent");
}