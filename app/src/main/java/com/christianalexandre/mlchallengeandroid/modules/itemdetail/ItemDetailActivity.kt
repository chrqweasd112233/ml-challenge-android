package com.christianalexandre.mlchallengeandroid.modules.itemdetail

import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.christianalexandre.mlchallengeandroid.R
import com.christianalexandre.mlchallengeandroid.databinding.ActivityItemDetailBinding
import com.christianalexandre.mlchallengeandroid.domain.model.ItemDetail
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem
import com.christianalexandre.mlchallengeandroid.modules.base.BaseActivity
import com.christianalexandre.mlchallengeandroid.modules.itemdetail.adapters.ItemDetailCarouselAdapter
import com.christianalexandre.mlchallengeandroid.modules.itemdetail.adapters.ItemDetailSpecAdapter
import com.christianalexandre.mlchallengeandroid.modules.util.constants.IntentConstants
import com.christianalexandre.mlchallengeandroid.modules.util.extensions.take
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityItemDetailBinding
    private val viewModel: ItemDetailViewModel by viewModels()
    private lateinit var searchItem: SearchItem

    // region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchItem = getExtra()
        setupContentView()
        setupTopBar(binding.includeTopBar.topBar, R.string.item_detail_title)
        setupLayoutInfo()
        setupObservers()

        viewModel.fetchInformation(searchItem.id!!)
    }
    // endregion

    // region Setup Methods
    private fun getExtra(): SearchItem {
        val jsonString = intent.getStringExtra(IntentConstants.SEARCH_ITEM)
        return Gson().fromJson(jsonString, SearchItem::class.java)
    }

    private fun setupContentView() {
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupLayoutInfo() {
        with(binding) {
            itemTitle.text = searchItem.title
            originalPriceTextView.isVisible = searchItem.originalPrice != null
            originalPriceTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            originalPriceTextView.text = searchItem.originalPrice.toString()
            priceTextView.text = searchItem.price.toString()
            freeShipping.isVisible = searchItem.freeShipping == true
            freeShipping.text = getString(R.string.free_shipping)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.itemsDetailState.collectLatest { itemDetailStateMachine(it) } }

                launch {
                    viewModel.itemDescriptionState.collectLatest {
                        itemDescriptionStateMachine(it)
                    }
                }
                launch {
                    viewModel.topLoadingState.collectLatest {
                        binding.topLoadingView.isVisible = it
                    }
                }
            }
        }
    }

    private fun setupCarouselRecyclerView(imageList: List<String>) {
        with(binding.carouselRecyclerView) {
            setLayoutManager(CarouselLayoutManager())
            adapter = ItemDetailCarouselAdapter(this@ItemDetailActivity, imageList)
        }
    }

    private fun setupSpecsView(specificationMap: Map<String, String>) {
        with(binding) {
            specificationButton.setOnClickListener { goToSpecsList(specificationMap) }
            with(specificationRecyclerView) {
                layoutManager = LinearLayoutManager(this@ItemDetailActivity)
                adapter = ItemDetailSpecAdapter(specificationMap.take(5))
            }
        }
    }
    // endregion

    // region Action Methods
    private fun itemDetailStateMachine(itemDetailUiState: ItemDetailUiState<ItemDetail>) {
        with(binding) {
            carouselLoadingView.isVisible = itemDetailUiState !is ItemDetailUiState.Success
            specificationLoadingView.isVisible = itemDetailUiState !is ItemDetailUiState.Success
            specificationRecyclerView.isVisible = itemDetailUiState is ItemDetailUiState.Success
            specificationButton.isVisible = itemDetailUiState is ItemDetailUiState.Success
        }

        when (itemDetailUiState) {
            is ItemDetailUiState.Error -> Toast.makeText(
                this,
                R.string.item_detail_error,
                Toast.LENGTH_SHORT
            ).show()

            is ItemDetailUiState.Success -> {
                val data = itemDetailUiState.data
                data.pictures?.let { setupCarouselRecyclerView(it) }
                data.attributes?.let { setupSpecsView(it) }
            }

            else -> {}
        }
    }

    private fun itemDescriptionStateMachine(itemDetailUiState: ItemDetailUiState<String>) {
        with(binding) {
            descriptionLoadingView.isVisible = itemDetailUiState !is ItemDetailUiState.Success
            descriptionTextView.isVisible = itemDetailUiState is ItemDetailUiState.Success
        }

        when (itemDetailUiState) {
            is ItemDetailUiState.Error -> Toast.makeText(
                this,
                R.string.item_detail_error,
                Toast.LENGTH_SHORT
            ).show()

            is ItemDetailUiState.Success -> binding.descriptionTextView.text =
                itemDetailUiState.data

            else -> {}
        }
    }

    private fun goToSpecsList(specificationMap: Map<String, String>) {
        Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show()
    }
    // endregion
}