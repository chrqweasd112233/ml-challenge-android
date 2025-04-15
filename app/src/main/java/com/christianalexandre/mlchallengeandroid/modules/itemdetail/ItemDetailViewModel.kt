package com.christianalexandre.mlchallengeandroid.modules.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christianalexandre.mlchallengeandroid.data.repository.ItemRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
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

    // region Public Observers
    val itemsDetailState: StateFlow<GenericUiState<ItemDetail>> = _itemsDetailState
    val itemDescriptionState: StateFlow<GenericUiState<String>> = _itemDescriptionState
    val topLoadingState: StateFlow<Boolean> = combine(
        _itemsDetailState, _itemDescriptionState
    ) { detailState, descriptionState ->
        detailState is GenericUiState.Loading || descriptionState is GenericUiState.Loading
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)
    // endregion

    fun fetchInformation(itemId: String) {
        viewModelScope.launch {
            launch {
                _itemsDetailState.value = GenericUiState.Loading
                _itemDescriptionState.value = GenericUiState.Loading
            }

            withContext(Dispatchers.IO) {
                launch {
                    _itemsDetailState.value = when (val result = repository.getItemDetail(itemId)) {
                        is ApiResponse.Error -> GenericUiState.Error(result.error?.code)
                        is ApiResponse.Success -> {
                            result.data?.let {
                                GenericUiState.Success(it)
                            } ?: GenericUiState.Error(500) // TODO: handle error
                        }
                    }
                }

                launch {
                    repository.getItemCategory(itemId)

                    _itemDescriptionState.value =
                        when (val result = repository.getItemDescription(itemId)) {
                            is ApiResponse.Error -> GenericUiState.Error(result.error?.code)
                            is ApiResponse.Success -> {
                                result.data?.let {
                                    GenericUiState.Success(it)
                                } ?: GenericUiState.Error(500) // TODO: handle error
                            }
                        }
                }
            }
        }
    }
}