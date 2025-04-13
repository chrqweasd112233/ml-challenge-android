package com.christianalexandre.mlchallengeandroid.modules.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christianalexandre.mlchallengeandroid.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    fun getItemDetail(itemId: String) {
        viewModelScope.launch {
            repository.getItemDetail(itemId)
        }
    }
}