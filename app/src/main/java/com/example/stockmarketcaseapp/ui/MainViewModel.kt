package com.example.stockmarketcaseapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketcaseapp.repository.remote.StocksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stockRepository: StocksRepository
): ViewModel() {

    //This method is only for testing the api call.
    fun fetchStockDataForTesting() {
        viewModelScope.launch {
            try {
                // Servis 1'den hisse senedi listesini ve sütun yapılandırmalarını al
                val service1Response = stockRepository.getService1Data()
                if (service1Response.isSuccessful) {
                    Log.d("MainViewModel", "Servis 1 Yanıtı: ${service1Response.body()}")

                    // Servis 1'den gelen hisse senedi anahtarlarını birleştir
                    val stockKeys = service1Response.body()?.mypageDefaults?.joinToString("~") { it.tke } ?: ""
                    Log.d("MainViewModel", "Hisse Senedi Anahtarları: $stockKeys")

                    // Servis 2'den hisse senedi verilerini al
                    val service2Response = stockRepository.getService2Data(
                        fields = "Last,DailyChangePercentage",
                        stocks = stockKeys
                    )

                    if (service2Response.isSuccessful) {
                        Log.d("MainViewModel", "Servis 2 Yanıtı: ${service2Response.body()}")
                    } else {
                        Log.e("MainViewModel", "Servis 2 Hatası: ${service2Response.message()}")
                    }
                } else {
                    Log.e("MainViewModel", "Servis 1 Hatası: ${service1Response.message()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Hata: ${e.message}")
            }
        }
    }

}