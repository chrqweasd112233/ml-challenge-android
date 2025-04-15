package com.christianalexandre.mlchallengeandroid.modules.itemdetail

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
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
import com.christianalexandre.mlchallengeandroid.modules.itemdetail.fragments.ItemCategoryFragment
import com.christianalexandre.mlchallengeandroid.modules.util.constants.IntentConstants
import com.christianalexandre.mlchallengeandroid.modules.util.extensions.take
import com.christianalexandre.mlchallengeandroid.modules.util.ui.generic.GenericUiState
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
    private lateinit var specAdapter: ItemDetailSpecAdapter

    // region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchItem = getExtra()
        setupContentView()
        setupTopBar(binding.includeTopBar.topBar, R.string.item_detail_title)
        if (savedInstanceState == null) setupFragments()
        setupLayoutInfo()
        setupObservers()
        setupShareButton()

        viewModel.fetchInformation(searchItem.id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_detail, menu)
        return super.onCreateOptionsMenu(menu)
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

    private fun setupFragments() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.category_fragment, ItemCategoryFragment())
            .commit()
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

    private fun setupExtraInformationView(extraInfo: Map<String, String>) {
        with(binding) {
            with(saleTermsRecyclerView) {
                layoutManager = LinearLayoutManager(this@ItemDetailActivity)
                adapter = ItemDetailSpecAdapter(extraInfo)
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
        specAdapter = ItemDetailSpecAdapter(specificationMap.take(5))
        with(binding) {
            specificationButton.isVisible = specificationMap.entries.size >= 5
            specificationButton.setOnClickListener { goToSpecsList(specificationMap) }
            with(specificationRecyclerView) {
                layoutManager = LinearLayoutManager(this@ItemDetailActivity)
                adapter = this@ItemDetailActivity.specAdapter
            }
        }
    }

    private fun setupShareButton() {
        binding.includeTopBar.topBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.share_button -> {
                    shareButtonTapped()
                    true
                }
                else -> false
            }
        }
    }
    // endregion

    // region Action Methods
    private fun itemDetailStateMachine(state: GenericUiState<ItemDetail>) {
        with(binding) {
            carouselLoadingView.isVisible = state is GenericUiState.Loading
            specificationLoadingView.isVisible = state is GenericUiState.Loading
            specificationRecyclerView.isVisible = state is GenericUiState.Success
            specificationButton.isVisible = state is GenericUiState.Success
            carouselErrorView.isVisible = state is GenericUiState.Error
            specificationErrorView.isVisible = state is GenericUiState.Error
        }

        when (state) {
            is GenericUiState.Error -> Toast.makeText(
                this,
                R.string.item_detail_error,
                Toast.LENGTH_SHORT
            ).show()

            is GenericUiState.Success -> {
                val data = state.data
                data.saleTerms?.let { setupExtraInformationView(it) }
                data.pictures?.let { setupCarouselRecyclerView(it) }
                data.attributes?.let { setupSpecsView(it) }
            }

            else -> {}
        }
    }

    private fun itemDescriptionStateMachine(state: GenericUiState<String>) {
        with(binding) {
            descriptionLoadingView.isVisible = state is GenericUiState.Loading
            descriptionTextView.isVisible = state is GenericUiState.Success
            descriptionErrorView.isVisible = state is GenericUiState.Error
        }

        when (state) {
            is GenericUiState.Error -> Toast.makeText(
                this,
                R.string.item_detail_error,
                Toast.LENGTH_SHORT
            ).show()

            is GenericUiState.Success -> binding.descriptionTextView.text =
                state.data

            else -> {}
        }
    }

    private fun goToSpecsList(specificationMap: Map<String, String>) {
        specAdapter.updateItems(specificationMap)
        binding.specificationButton.isVisible = false
    }

    private fun shareButtonTapped() {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, searchItem.permalink)
            type = "text/plain"
        }, null)
        startActivity(share)
    }
    // endregion
}