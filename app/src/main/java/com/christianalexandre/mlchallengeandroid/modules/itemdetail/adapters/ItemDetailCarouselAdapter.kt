package com.christianalexandre.mlchallengeandroid.modules.itemdetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.christianalexandre.mlchallengeandroid.databinding.ItemCarrouselBinding

class ItemDetailCarouselAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<ItemDetailCarouselAdapter.CarouselViewHolder>() {

    inner class CarouselViewHolder(
        binding: ItemCarrouselBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.carouselImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = ItemCarrouselBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarouselViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val imageUrl = items[position]

        Glide.with(holder.imageView.context)
            .load(imageUrl)
            .centerCrop()
            .into(holder.imageView)
    }
}