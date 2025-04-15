package com.christianalexandre.mlchallengeandroid.modules.itemdetail.fragments

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.christianalexandre.mlchallengeandroid.modules.util.ui.views.ItemCategoryView

class ItemCategoryAdapter :
    ListAdapter<String, ItemCategoryAdapter.ItemCategoryViewHolder>(DiffCallback) {

    inner class ItemCategoryViewHolder(
        val view: ItemCategoryView
    ) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryViewHolder {
        return ItemCategoryViewHolder(ItemCategoryView(parent.context))
    }

    override fun onBindViewHolder(holder: ItemCategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.textView.text = item
    }

    object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}