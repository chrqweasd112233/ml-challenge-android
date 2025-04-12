package com.christianalexandre.mlchallengeandroid.modules.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.christianalexandre.mlchallengeandroid.R
import com.christianalexandre.mlchallengeandroid.databinding.ActivityHomeBinding
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem
import com.christianalexandre.mlchallengeandroid.modules.base.BaseActivity
import com.christianalexandre.mlchallengeandroid.modules.home.adapters.SearchHistoryAdapter
import com.christianalexandre.mlchallengeandroid.modules.home.adapters.SearchItemsAdapter
import com.christianalexandre.mlchallengeandroid.modules.util.manager.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var historyAdapter: SearchHistoryAdapter
    private lateinit var searchItemsAdapter: SearchItemsAdapter
    @Inject lateinit var preferencesManager: PreferencesManager


    // region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupContentView()
        setupSearchBar()
        setupHistoryRecyclerView()
        setupSearchItemsRecyclerView()
        setupObservers()
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

            appBar.setOnClickListener {
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

    private fun setupSearchItemsRecyclerView() {
        searchItemsAdapter = SearchItemsAdapter(emptyList())
        with(binding.searchItemsRecyclerView) {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = this@HomeActivity.searchItemsAdapter
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventsState.collectLatest { searchStateMachine(it) }
            }
        }
    }
    // endregion

    // region Action Methods
    private fun searchFor(query: String) {
        preferencesManager.searchHistory = listOf(query)
        binding.searchView.hide()
        viewModel.fetchItems(query)
    }

    private fun searchStateMachine(state: HomeUiState<List<SearchItem>>) {
        with(binding) {
            textView.isVisible = state is HomeUiState.Uninitialized || state is HomeUiState.Error
            progressIndicator.isVisible = state is HomeUiState.Loading
            searchItemsRecyclerView.isVisible = state is HomeUiState.Success

            when(state) {
                is HomeUiState.Empty -> textView.text = getString(R.string.search_empty)
                is HomeUiState.Error -> textView.text = getString(R.string.search_error, state.code)
                is HomeUiState.Success -> searchItemsAdapter.updateItems(state.data)
                else -> { }
            }
        }

    }
    // endregion
}