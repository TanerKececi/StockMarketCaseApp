package com.example.stockmarketcaseapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmarketcaseapp.databinding.ItemSelectionBinding
import com.example.stockmarketcaseapp.model.ColumnItem

class SelectionAdapter(
    private val onItemClicked: (ColumnItem) -> Unit
) : ListAdapter<ColumnItem, SelectionAdapter.SelectionViewHolder>(ItemDiffCallback()) {

    // DiffUtil callback
    class ItemDiffCallback : DiffUtil.ItemCallback<ColumnItem>() {
        override fun areItemsTheSame(oldItem: ColumnItem, newItem: ColumnItem): Boolean {
            return oldItem.key == newItem.key // key'e göre kıyaslama
        }

        override fun areContentsTheSame(oldItem: ColumnItem, newItem: ColumnItem): Boolean {
            return oldItem == newItem // İçerik karşılaştırması
        }
    }

    // ViewHolder
    inner class SelectionViewHolder(private val binding: ItemSelectionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ColumnItem) {
            binding.item = item
            binding.isSelected = item.isSelected
            binding.root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    // onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        val binding = ItemSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectionViewHolder(binding)
    }

    // onBindViewHolder
    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
