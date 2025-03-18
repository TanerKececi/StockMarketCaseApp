package com.example.stockmarketcaseapp.repository.remote

import com.example.stockmarketcaseapp.model.Service1Response
import com.example.stockmarketcaseapp.model.StockData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StocksApiService {
    @GET("default/ForeksMobileInterviewSettings")
    suspend fun getService1Data(): Response<Service1Response>

    @GET("default/ForeksMobileInterview")
    suspend fun getService2Data(
        @Query("fields") fields: String,
        @Query("stcs") stocks: String
    ): Response<List<StockData>>
}