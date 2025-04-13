package com.christianalexandre.mlchallengeandroid.modules.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christianalexandre.mlchallengeandroid.data.repository.ItemRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.model.ItemDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ItemDetailUiState {
    data object Uninitialized : ItemDetailUiState()
    data object Loading : ItemDetailUiState()
    data class Success(val data: ItemDetail) : ItemDetailUiState()
    data class Error(val code: Int?) : ItemDetailUiState()
}

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _itemsDetailState = MutableStateFlow<ItemDetailUiState>(ItemDetailUiState.Uninitialized)

    // region Public Observers
    val itemsDetailState: StateFlow<ItemDetailUiState> = _itemsDetailState
    // endregion

    fun fetchInformation(itemId: String) {
        viewModelScope.launch {
            _itemsDetailState.value = ItemDetailUiState.Loading
            _itemsDetailState.value = when(val result = repository.getItemDetail(itemId)) {
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