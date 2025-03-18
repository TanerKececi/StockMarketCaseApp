package com.example.stockmarketcaseapp.repository.remote

import com.example.stockmarketcaseapp.model.Service1Response
import com.example.stockmarketcaseapp.model.StockData
import retrofit2.Response
import javax.inject.Inject

class StocksRepositoryImpl @Inject constructor(
    private val stockApi: StocksApiService
) : StocksRepository {

    override suspend fun getService1Data(): Response<Service1Response> {
        return stockApi.getService1Data()
    }

    override suspend fun getService2Data(fields: String, stocks: String): Response<List<StockData>> {
        return stockApi.getService2Data(fields, stocks)
    }
}