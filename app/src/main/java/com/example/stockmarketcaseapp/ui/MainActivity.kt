package com.example.stockmarketcaseapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmarketcaseapp.R
import com.example.stockmarketcaseapp.databinding.ActivityMainBinding
import com.example.stockmarketcaseapp.ui.adapter.StockAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = viewModel
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rvStockItems.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = StockAdapter()
        binding.rvStockItems.adapter = adapter
        viewModel.fetchStockDataForTesting()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.stockDataList.observe(this) { stockDataList ->
            adapter.submitList(stockDataList)
        }
        viewModel.firstFilter.observe(this) { firstFilter ->
            binding.tvColumn1.text = firstFilter.name
        }
        viewModel.secondFilter.observe(this) { secondFilter ->
            binding.tvColumn2.text = secondFilter.name

        }
    }
}