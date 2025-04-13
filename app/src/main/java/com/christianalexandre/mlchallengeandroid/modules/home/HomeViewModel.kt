package com.christianalexandre.mlchallengeandroid.modules.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christianalexandre.mlchallengeandroid.data.repository.ItemRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState<out T> {
    data object Uninitialized : HomeUiState<Nothing>()
    data object Loading : HomeUiState<Nothing>()
    data class Success<out T>(val data: T) : HomeUiState<T>()
    data class Error(val code: Int?) : HomeUiState<Nothing>()
    data object Empty : HomeUiState<Nothing>()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _searchItemsState = MutableStateFlow<HomeUiState<List<SearchItem>>>(HomeUiState.Uninitialized)

    // region Public Observers
    val searchItemsState: StateFlow<HomeUiState<List<SearchItem>>> = _searchItemsState
    // endregion

    fun fetchItems(query: String) {
        viewModelScope.launch {
            _searchItemsState.value = HomeUiState.Loading
            _searchItemsState.value = when (val result = repository.search(query)) {
                is ApiResponse.Error -> HomeUiState.Error(result.error?.code)
                is ApiResponse.Success -> {
                    result.data?.let {
                        if (it.isNotEmpty()) HomeUiState.Success(it)
                        else HomeUiState.Empty
                    } ?: HomeUiState.Empty
                }
            }

        }
    }
}