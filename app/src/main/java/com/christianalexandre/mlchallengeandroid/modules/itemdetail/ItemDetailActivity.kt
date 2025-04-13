package com.christianalexandre.mlchallengeandroid.modules.itemdetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.christianalexandre.mlchallengeandroid.R
import com.christianalexandre.mlchallengeandroid.databinding.ActivityItemDetailBinding
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem
import com.christianalexandre.mlchallengeandroid.modules.base.BaseActivity
import com.christianalexandre.mlchallengeandroid.modules.itemdetail.adapters.ItemDetailCarouselAdapter
import com.christianalexandre.mlchallengeandroid.modules.util.constants.IntentConstants
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
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemsDetailState.collectLatest { setupItemDetail(it) }
            }
        }
    }

    private fun setupItemDetail(itemDetailUiState: ItemDetailUiState) {
        when(itemDetailUiState) {
            is ItemDetailUiState.Success -> {
                setupCarouselRecyclerView(itemDetailUiState.data.pictures!!)
            }
            else -> {}
        }
    }

    private fun setupCarouselRecyclerView(imageList: List<String>) {
        with(binding.carouselRecyclerView) {
            setLayoutManager(CarouselLayoutManager())
            adapter = ItemDetailCarouselAdapter(imageList)
        }
    }
    // endregion

}