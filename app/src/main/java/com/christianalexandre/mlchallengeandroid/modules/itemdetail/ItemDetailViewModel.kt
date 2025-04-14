package com.christianalexandre.mlchallengeandroid.modules.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christianalexandre.mlchallengeandroid.data.repository.ItemRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.model.ItemDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class ItemDetailUiState<out T> {
    data object Uninitialized : ItemDetailUiState<Nothing>()
    data object Loading : ItemDetailUiState<Nothing>()
    data class Success<out T>(val data: T) : ItemDetailUiState<T>()
    data class Error(val code: Int?) : ItemDetailUiState<Nothing>()
}

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _itemsDetailState =
        MutableStateFlow<ItemDetailUiState<ItemDetail>>(ItemDetailUiState.Uninitialized)
    private val _itemDescriptionState =
        MutableStateFlow<ItemDetailUiState<String>>(ItemDetailUiState.Uninitialized)

    // region Public Observers
    val itemsDetailState: StateFlow<ItemDetailUiState<ItemDetail>> = _itemsDetailState
    val itemDescriptionState: StateFlow<ItemDetailUiState<String>> = _itemDescriptionState
    val topLoadingState: StateFlow<Boolean> = combine(
        _itemsDetailState, _itemDescriptionState
    ) { detailState, descriptionState ->
        detailState is ItemDetailUiState.Loading || descriptionState is ItemDetailUiState.Loading
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)
    // endregion

    fun fetchInformation(itemId: String) {
        viewModelScope.launch {
            launch {
                _itemsDetailState.value = ItemDetailUiState.Loading
                _itemDescriptionState.value = ItemDetailUiState.Loading
            }

            withContext(Dispatchers.IO) {
                launch {
                    _itemsDetailState.value = when (val result = repository.getItemDetail(itemId)) {
                        is ApiResponse.Error -> ItemDetailUiState.Error(result.error?.code)
                        is ApiResponse.Success -> {
                            result.data?.let {
                                ItemDetailUiState.Success(it)
                            } ?: ItemDetailUiState.Error(500) // TODO: handle error
                        }
                    }
                }

                launch {
                    _itemDescriptionState.value =
                        when (val result = repository.getItemDescription(itemId)) {
                            is ApiResponse.Error -> ItemDetailUiState.Error(result.error?.code)
                            is ApiResponse.Success -> {
                                result.data?.let {
                                    ItemDetailUiState.Success(it)
                                } ?: ItemDetailUiState.Error(500) // TODO: handle error
                            }
                        }
                }
            }
        }
    }
}