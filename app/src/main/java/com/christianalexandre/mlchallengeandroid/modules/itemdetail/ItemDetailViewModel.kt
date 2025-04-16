package com.christianalexandre.mlchallengeandroid.modules.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christianalexandre.mlchallengeandroid.domain.repository.ItemRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.model.ItemCategory
import com.christianalexandre.mlchallengeandroid.domain.model.ItemDetail
import com.christianalexandre.mlchallengeandroid.modules.util.ui.generic.GenericUiState
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

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _itemsDetailState =
        MutableStateFlow<GenericUiState<ItemDetail>>(GenericUiState.Uninitialized)
    private val _itemDescriptionState =
        MutableStateFlow<GenericUiState<String>>(GenericUiState.Uninitialized)
    private val _itemCategoryState =
        MutableStateFlow<GenericUiState<ItemCategory>>(GenericUiState.Uninitialized)

    // region Public Observers
    val itemsDetailState: StateFlow<GenericUiState<ItemDetail>> = _itemsDetailState
    val itemDescriptionState: StateFlow<GenericUiState<String>> = _itemDescriptionState
    val itemCategoryState: StateFlow<GenericUiState<ItemCategory>> = _itemCategoryState
    val topLoadingState: StateFlow<Boolean> = combine(
        _itemsDetailState, _itemDescriptionState, _itemCategoryState
    ) { detailState, descriptionState, itemCategoryState ->
        detailState is GenericUiState.Loading ||
                descriptionState is GenericUiState.Loading ||
                itemCategoryState is GenericUiState.Loading
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)
    // endregion

    fun fetchInformation(itemId: String?) {
        if (itemId == null) return
        fetchDetail(itemId)
        fetchDescription(itemId)
        fetchCategory(itemId)
    }

    fun fetchDetail(itemId: String) {
        viewModelScope.launch {
            _itemsDetailState.value = GenericUiState.Loading
            withContext(Dispatchers.IO) {
                _itemsDetailState.value = handleResult(repository.getItemDetail(itemId))
            }
        }
    }

    fun fetchDescription(itemId: String) {
        viewModelScope.launch {
            _itemDescriptionState.value = GenericUiState.Loading
            withContext(Dispatchers.IO) {
                _itemDescriptionState.value = handleResult(repository.getItemDescription(itemId))
            }
        }
    }

    fun fetchCategory(itemId: String) {
        viewModelScope.launch {
            _itemCategoryState.value = GenericUiState.Loading
            withContext(Dispatchers.IO) {
                _itemCategoryState.value = handleResult(repository.getItemCategory(itemId))
            }
        }
    }

    private fun <T> handleResult(result: ApiResponse<T?>): GenericUiState<T> {
        return when (result) {
            is ApiResponse.Error -> GenericUiState.Error(result.error?.code)
            is ApiResponse.Success -> {
                result.data?.let {
                    GenericUiState.Success(it)
                } ?: GenericUiState.Error(result.error?.code)
            }
        }
    }
}