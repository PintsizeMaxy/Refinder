package com.max.refinder.models

sealed class ViewState<out T> {
    data object Loading : ViewState<Nothing>()

    data object Failed : ViewState<Nothing>()
    data class Success<out T>(val data: T): ViewState<T>()
}