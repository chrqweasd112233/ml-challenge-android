package com.christianalexandre.mlchallengeandroid.modules.home.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.christianalexandre.mlchallengeandroid.R
import com.christianalexandre.mlchallengeandroid.databinding.ItemSearchBinding
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem
import com.christianalexandre.mlchallengeandroid.modules.util.extensions.startAnimation


class SearchItemsAdapter(
    private val context: Context,
    private val onItemClickCallback: (SearchItem) -> Unit
) : ListAdapter<SearchItem, SearchItemsAdapter.SearchItemsViewHolder>(DiffCallback) {

    inner class SearchItemsViewHolder(
        val binding: ItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.itemImageView
        val titleTextView = binding.titleTextView
        val sellerTextView = binding.sellerTextView
        val originalPriceTextView = binding.originalPriceTextView
        val priceTextView = binding.priceTextView
        val shippingTextView = binding.shippingTextView
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
        val item = getItem(position)

        Glide.with(holder.imageView.context)
            .load(item.thumbnail)
            .fitCenter()
            .placeholder(AppCompatResources.getDrawable(context, R.drawable.loading_spinner)?.startAnimation())
            .error(R.drawable.ic_error)
            .into(holder.imageView)

        with(holder) {
            titleTextView.text = item.title
            sellerTextView.isVisible = item.officialStoreName != null
            sellerTextView.text = item.officialStoreName
            originalPriceTextView.isVisible = item.originalPrice != null
            originalPriceTextView.text = item.originalPrice.toString()
            originalPriceTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            priceTextView.isVisible = item.price != null
            priceTextView.text = item.price.toString()
            shippingTextView.isVisible = item.freeShipping == true
            shippingTextView.text = context.getString(R.string.free_shipping)

            binding.root.setOnClickListener { onItemClickCallback(item) }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem
        }
    }
}