package com.christianalexandre.mlchallengeandroid.modules.itemdetail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.christianalexandre.mlchallengeandroid.databinding.FragmentCategoryBinding
import com.christianalexandre.domain.model.ItemCategory
import com.christianalexandre.mlchallengeandroid.modules.itemdetail.ItemDetailViewModel
import com.christianalexandre.mlchallengeandroid.modules.util.ui.generic.GenericUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemCategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val viewModel: ItemDetailViewModel by activityViewModels()

    // region Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }
    // endregion

    // region Setup Methods
    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemCategoryState.collectLatest { itemCategoryStateMachine(it) }
            }
        }
    }

    private fun setupRecyclerView(list: List<String>?) {
        with(binding.categoryRecyclerView) {
            layoutManager = LinearLayoutManager(
                this@ItemCategoryFragment.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = ItemCategoryAdapter().apply {
                submitList(list)
            }
        }
    }
    // endregion

    // region Action Methods
    private fun itemCategoryStateMachine(state: GenericUiState<ItemCategory>) {
        with(binding) {
            categoryLoadingView.isVisible = state is GenericUiState.Loading
            categoryErrorView.isVisible = state is GenericUiState.Error
            categoryRecyclerView.isVisible = state is GenericUiState.Success
        }

        when(state) {
            is GenericUiState.Success -> setupRecyclerView(state.data.pathFromRoot)
            else -> { }
        }
    }
    // endregion
}