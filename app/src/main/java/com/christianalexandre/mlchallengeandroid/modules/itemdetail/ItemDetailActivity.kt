package com.christianalexandre.mlchallengeandroid.modules.itemdetail

import android.os.Bundle
import com.christianalexandre.mlchallengeandroid.R
import com.christianalexandre.mlchallengeandroid.databinding.ActivityItemDetailBinding
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem
import com.christianalexandre.mlchallengeandroid.modules.base.BaseActivity
import com.christianalexandre.mlchallengeandroid.modules.util.constants.IntentConstants
import com.google.gson.Gson

class ItemDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityItemDetailBinding
    private lateinit var searchItem: SearchItem

    // region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchItem = getExtra()
        setupContentView()
        setupTopBar(binding.includeTopBar.topBar, R.string.item_detail_title)
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
    // endregion

}