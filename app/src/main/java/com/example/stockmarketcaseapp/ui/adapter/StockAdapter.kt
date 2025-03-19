package com.example.stockmarketcaseapp.ui.adapter

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmarketcaseapp.databinding.ItemStockBinding
import com.example.stockmarketcaseapp.model.StockDataUiModel

class StockAdapter : ListAdapter<StockDataUiModel, StockAdapter.StockViewHolder>(StockDiffCallback()) {

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
        fun bind(stock: StockDataUiModel) {
            binding.stock = stock
            binding.textColorResource = stock.textColor
            binding.executePendingBindings()
            val animator = ValueAnimator.ofArgb(Color.GRAY, Color.TRANSPARENT).apply {
                duration = 1000
                addUpdateListener { animator ->
                    binding.root.setBackgroundColor(animator.animatedValue as Int)
                }
            }
            animator.start()
        }
    }

    class StockDiffCallback : DiffUtil.ItemCallback<StockDataUiModel>() {
        override fun areItemsTheSame(oldItem: StockDataUiModel, newItem: StockDataUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StockDataUiModel, newItem: StockDataUiModel): Boolean {
            return oldItem == newItem
        }
    }
}