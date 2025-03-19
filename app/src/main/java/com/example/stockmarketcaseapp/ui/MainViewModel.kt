package com.example.stockmarketcaseapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketcaseapp.model.ColumnItem
import com.example.stockmarketcaseapp.model.Filter
import com.example.stockmarketcaseapp.model.StockData
import com.example.stockmarketcaseapp.model.StockDataUiModel
import com.example.stockmarketcaseapp.model.StockItem
import com.example.stockmarketcaseapp.model.mapToUiModel
import com.example.stockmarketcaseapp.model.toFilter
import com.example.stockmarketcaseapp.repository.remote.StocksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stockRepository: StocksRepository
): ViewModel() {

    private val _stockDataList = MutableLiveData<List<StockDataUiModel>>()
    val stockDataList: LiveData<List<StockDataUiModel>> get() = _stockDataList

    private val _firstFilter = MutableLiveData<ColumnItem>()
    val firstFilter: LiveData<ColumnItem> get() = _firstFilter

    private val _secondFilter = MutableLiveData<ColumnItem>()
    val secondFilter: LiveData<ColumnItem> get() = _secondFilter

    private var stockItemList: List<StockItem> = listOf()
    private var filterList: List<ColumnItem> = listOf()

    //This method is only for testing the api call.
    fun fetchStockDataForTesting() {
        viewModelScope.launch {
            try {
                val service1Response = stockRepository.getService1Data()
                if (service1Response.isSuccessful) {
                    Log.d("MainViewModel", "Servis 1 Yanıtı: ${service1Response.body()}")

                    stockItemList = service1Response.body()?.mypageDefaults ?: listOf()
                    filterList = service1Response.body()?.mypage ?: listOf()
                    val stockKeys = stockItemList.joinToString("~") { it.tke }
                    val fields = filterList.take(2).joinToString(",") { it.key }
                    _firstFilter.postValue(filterList[0])
                    _secondFilter.postValue(filterList[1])
                    Log.d("MainViewModel", "Hisse Senedi Anahtarları: $stockKeys")
                    val service2Response = stockRepository.getService2Data(
                        fields = fields,
                        stocks = stockKeys
                    )

                    if (service2Response.isSuccessful) {
                        val selectedFilters = listOf(_firstFilter.value?.toFilter() ?: Filter.SON, _secondFilter.value?.toFilter() ?: Filter.PERCENT_CHANGE)
                        Log.d("MainViewModel", "Servis 2 Yanıtı: ${service2Response.body()}")
                        val uiModelList = service2Response.body()?.map { it.mapToUiModel(selectedFilters)} ?: listOf()
                        _stockDataList.postValue(uiModelList)  // Verileri LiveData'ya aktar
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