package com.christianalexandre.mlchallengeandroid.modules.home.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.christianalexandre.mlchallengeandroid.modules.util.views.SearchHistoryItemView

class SearchHistoryAdapter(
    private val onItemClickCallback: (String) -> Unit
) : ListAdapter<String, SearchHistoryAdapter.SearchHistoryViewHolder>(DiffCallback) {

    inner class SearchHistoryViewHolder(
        val view: SearchHistoryItemView
    ) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return SearchHistoryViewHolder(SearchHistoryItemView(parent.context))
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.textView.text = item
        holder.view.setOnClickListener { onItemClickCallback(item) }
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