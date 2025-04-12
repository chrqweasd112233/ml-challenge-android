package com.christianalexandre.mlchallengeandroid.modules.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christianalexandre.mlchallengeandroid.data.repository.SearchRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState<T> {
    class Uninitialized<T> : HomeUiState<T>()
    class Loading<T> : HomeUiState<T>()
    class Success<T>(val data: T) : HomeUiState<T>()
    class Error<T>(val code: Int?) : HomeUiState<T>()
    class Empty<T> : HomeUiState<T>()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _eventsState = MutableStateFlow<HomeUiState<List<SearchItem>>>(HomeUiState.Uninitialized())

    // region Public Observers
    val eventsState: StateFlow<HomeUiState<List<SearchItem>>> = _eventsState
    // endregion

    fun fetchItems(query: String) {
        viewModelScope.launch {
            _eventsState.value = HomeUiState.Loading()
            _eventsState.value = when (val result = repository.search(query)) {
                is ApiResponse.Error -> HomeUiState.Error(result.error?.code)
                is ApiResponse.Success -> {
                    result.data?.let {
                        if (it.isNotEmpty()) HomeUiState.Success(it)
                        else HomeUiState.Empty()
                    } ?: HomeUiState.Empty()
                }
            }

        }
    }
}