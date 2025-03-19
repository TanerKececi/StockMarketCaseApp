package com.example.stockmarketcaseapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmarketcaseapp.databinding.ItemStockBinding
import com.example.stockmarketcaseapp.model.StockData

class StockAdapter : ListAdapter<StockData, StockAdapter.StockViewHolder>(StockDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStockBinding.inflate(inflater, parent, false)
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = getItem(position)
        holder.bind(stock)
    }

    class StockViewHolder(private val binding: ItemStockBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stock: StockData) {
            binding.stock = stock
            binding.executePendingBindings()
        }
    }

    class StockDiffCallback : DiffUtil.ItemCallback<StockData>() {
        override fun areItemsTheSame(oldItem: StockData, newItem: StockData): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: StockData, newItem: StockData): Boolean {
            return oldItem == newItem
        }
    }
}