package com.example.stockmarketcaseapp.repository.remote

import com.example.stockmarketcaseapp.model.Service1Response
import com.example.stockmarketcaseapp.model.StockData
import retrofit2.Response

interface StocksRepository {
    suspend fun getService1Data(): Response<Service1Response>

    suspend fun getService2Data(fields: String, stocks: String): Response<List<StockData>>
}