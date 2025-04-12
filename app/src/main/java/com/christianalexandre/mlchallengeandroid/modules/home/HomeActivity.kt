package com.christianalexandre.mlchallengeandroid.modules.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.christianalexandre.mlchallengeandroid.R
import com.christianalexandre.mlchallengeandroid.databinding.ActivityHomeBinding
import com.christianalexandre.mlchallengeandroid.domain.repository.SearchRepositoryImpl
import com.christianalexandre.mlchallengeandroid.modules.base.BaseActivity
import com.christianalexandre.mlchallengeandroid.modules.home.adapters.SearchHistoryAdapter
import com.christianalexandre.mlchallengeandroid.modules.util.manager.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var historyAdapter: SearchHistoryAdapter
    @Inject
    lateinit var searchRepositoryImpl: SearchRepositoryImpl
    @Inject
    lateinit var preferencesManager: PreferencesManager

    // region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupContentView()
        setupSearchBar()
        setupHistoryRecyclerView()
    }

    // endregion

    // region Setup Methods
    private fun setupContentView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupSearchBar() {
        with(binding) {
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
                ) {
                    searchFor(textView.text.toString())
                    true
                } else false
            }

            searchView.setOnClickListener {
                historyAdapter.updateItems(preferencesManager.searchHistory)
                searchView.show()
            }
        }
    }

    private fun setupHistoryRecyclerView() {
        historyAdapter = SearchHistoryAdapter(preferencesManager.searchHistory) { searchFor(it) }
        with(binding.historyRecyclerView) {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = this@HomeActivity.historyAdapter
        }
    }
    // endregion

    // region Action Methods
    private fun searchFor(item: String) {
        preferencesManager.searchHistory = listOf(item)
        binding.searchView.hide()
    }
    // endregion
}