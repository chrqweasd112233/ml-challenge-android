package com.christianalexandre.mlchallengeandroid.modules.util.ui.generic

sealed class GenericUiState<out T> {
    data object Uninitialized : GenericUiState<Nothing>()
    data object Loading : GenericUiState<Nothing>()
    data class Success<out T>(val data: T) : GenericUiState<T>()
    data class Error(val code: Int?) : GenericUiState<Nothing>()
    data object Empty : GenericUiState<Nothing>()
}