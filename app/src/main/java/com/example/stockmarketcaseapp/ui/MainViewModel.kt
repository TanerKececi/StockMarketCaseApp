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

    private val selectedFilters: List<Filter>
        get() = listOf(_firstFilter.value?.toFilter() ?: Filter.SON, _secondFilter.value?.toFilter() ?: Filter.PERCENT_CHANGE)

    private var allStockItemsWithDefinitions: List<StockItem> = listOf()

    var allFilterList: List<ColumnItem> = listOf()
        private set

    private var allApiStocksCache: List<StockData> = listOf()

    fun setFirstFilter(newSelectedItem: ColumnItem) {
        val oldSelectedItem = _firstFilter.value ?: return
        val itemInList = allFilterList.find { it.key == newSelectedItem.key } ?: return
        itemInList.isSelected = itemInList.isSelected.not()
        oldSelectedItem.isSelected = oldSelectedItem.isSelected.not()
        _firstFilter.value = itemInList
        reFilterStocks()
    }

    fun setSecondFilter(newSelectedItem: ColumnItem) {
        val oldSelectedItem = _secondFilter.value ?: return
        val itemInList = allFilterList.find { it.key == newSelectedItem.key } ?: return
        itemInList.isSelected = itemInList.isSelected.not()
        oldSelectedItem.isSelected = oldSelectedItem.isSelected.not()
        _secondFilter.value = itemInList
        reFilterStocks()
    }

    //This method is only for testing the api call.
    fun fetchStockDataForTesting() {
        viewModelScope.launch {
            try {
                val service1Response = stockRepository.getService1Data()
                if (service1Response.isSuccessful) {
                    Log.d("MainViewModel", "Servis 1 Yanıtı: ${service1Response.body()}")

                    allStockItemsWithDefinitions = service1Response.body()?.mypageDefaults ?: listOf()
                    allFilterList = service1Response.body()?.mypage ?: listOf()
                    val stockKeys = allStockItemsWithDefinitions.joinToString("~") { it.tke }
                    val fields = allFilterList.joinToString(",") { it.key }
                    allFilterList[0].isSelected = true
                    _firstFilter.postValue(allFilterList[0])
                    allFilterList[1].isSelected = true
                    _secondFilter.postValue(allFilterList[1])
                    Log.d("MainViewModel", "Hisse Senedi Anahtarları: $stockKeys")
                    val service2Response = stockRepository.getService2Data(
                        fields = fields,
                        stocks = stockKeys
                    )

                    if (service2Response.isSuccessful) {
                        Log.d("MainViewModel", "Servis 2 Yanıtı: ${service2Response.body()}")
                        allApiStocksCache = service2Response.body() ?: listOf()
                        val uiModelList = allApiStocksCache.map { it.mapToUiModel(selectedFilters, allStockItemsWithDefinitions)}
                        _stockDataList.postValue(uiModelList)
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

    fun reFilterStocks() {
        val uiModelList = allApiStocksCache.map { it.mapToUiModel(selectedFilters, allStockItemsWithDefinitions)}
        _stockDataList.postValue(uiModelList)
    }

}