package com.christianalexandre.mlchallengeandroid.modules.itemdetail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.christianalexandre.mlchallengeandroid.databinding.ItemSpecificationBinding

class ItemDetailSpecAdapter(
    var items: Map<String, String>
) : RecyclerView.Adapter<ItemDetailSpecAdapter.SpecificationViewHolder>() {

    inner class SpecificationViewHolder(
        binding: ItemSpecificationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val key = binding.key
        val value = binding.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificationViewHolder {
        val binding = ItemSpecificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpecificationViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SpecificationViewHolder, position: Int) {
        val item = items.entries.elementAt(position)

        with(holder) {
            key.text = item.key
            value.text = item.value
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: Map<String, String>) {
        this.items = items
        notifyDataSetChanged()
    }
}