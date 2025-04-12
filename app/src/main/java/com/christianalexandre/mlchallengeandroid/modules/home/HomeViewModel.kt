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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _eventsState = MutableStateFlow<ApiResponse<List<SearchItem>?>>(ApiResponse.Uninitialized())

    // region Public Observers
    val eventsState: StateFlow<ApiResponse<List<SearchItem>?>> = _eventsState
    // endregion

    fun fetchItems(query: String) {
        viewModelScope.launch {
            _eventsState.value = ApiResponse.Loading()
            _eventsState.value = repository.search(query = query)
        }
    }
}