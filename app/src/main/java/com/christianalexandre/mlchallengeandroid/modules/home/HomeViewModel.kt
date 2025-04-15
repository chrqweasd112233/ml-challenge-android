package com.christianalexandre.mlchallengeandroid.modules.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christianalexandre.mlchallengeandroid.data.repository.ItemRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem
import com.christianalexandre.mlchallengeandroid.modules.util.ui.generic.GenericUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _searchItemsState =
        MutableStateFlow<GenericUiState<List<SearchItem>>>(GenericUiState.Uninitialized)

    // region Public Observers
    val searchItemsState: StateFlow<GenericUiState<List<SearchItem>>> = _searchItemsState
    // endregion

    fun fetchItems(query: String) {
        viewModelScope.launch {
            _searchItemsState.value = GenericUiState.Loading
            _searchItemsState.value = when (val result = repository.search(query)) {
                is ApiResponse.Error -> GenericUiState.Error(result.error?.code)
                is ApiResponse.Success -> {
                    result.data?.let {
                        if (it.isNotEmpty()) GenericUiState.Success(it)
                        else GenericUiState.Empty
                    } ?: GenericUiState.Error(result.error?.code)
                }
            }

        }
    }
}