package com.christianalexandre.mlchallengeandroid.modules.itemdetail.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.christianalexandre.mlchallengeandroid.R
import com.christianalexandre.mlchallengeandroid.databinding.ItemCarrouselBinding
import com.christianalexandre.mlchallengeandroid.modules.util.extensions.startAnimation

class ItemDetailCarouselAdapter(
    private val context: Context,
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
            .fitCenter()
            .placeholder(AppCompatResources.getDrawable(context, R.drawable.loading_spinner)?.startAnimation())
            .error(R.drawable.ic_error)
            .into(holder.imageView)
    }
}