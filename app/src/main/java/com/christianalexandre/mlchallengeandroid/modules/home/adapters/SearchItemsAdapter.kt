package com.christianalexandre.mlchallengeandroid.modules.home.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.christianalexandre.mlchallengeandroid.databinding.ItemSearchBinding
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem

class SearchItemsAdapter(
    private var items: List<SearchItem>
) : RecyclerView.Adapter<SearchItemsAdapter.SearchItemsViewHolder>() {

    inner class SearchItemsViewHolder(
        binding: ItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.itemImageView
        val titleTextView = binding.titleTextView
        val subtitleTextView = binding.subtitleTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemsViewHolder {
        val binding = ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchItemsViewHolder, position: Int) {
        val item = items[position]

        Glide.with(holder.imageView.context)
            .load(Uri.parse(item.thumbnail))
            .centerCrop()
            .into(holder.imageView)

        holder.titleTextView.text = item.title
        holder.subtitleTextView.text = item.originalPrice.toString()
    }

    override fun getItemCount() = items.size

    // TODO("NotifyDataSetChanged")
    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newList: List<SearchItem>) {
        this.items = newList
        notifyDataSetChanged()
    }
}