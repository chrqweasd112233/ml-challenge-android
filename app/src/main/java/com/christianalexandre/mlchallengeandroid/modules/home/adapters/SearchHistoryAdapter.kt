package com.christianalexandre.mlchallengeandroid.modules.home.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.christianalexandre.mlchallengeandroid.modules.util.views.SearchHistoryItemView

class SearchHistoryAdapter(
    private var items: List<String>,
    private val onItemClickCallback: (String) -> Unit
) : RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {

    inner class SearchHistoryViewHolder(
        val view: SearchHistoryItemView
    ) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return SearchHistoryViewHolder(SearchHistoryItemView(parent.context))
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val item = items[position]
        holder.view.textView.text = item
        holder.view.setOnClickListener { onItemClickCallback(item) }
    }

    override fun getItemCount() = items.size

    // TODO: Avoid NotifyDataSetChanged
    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }
}