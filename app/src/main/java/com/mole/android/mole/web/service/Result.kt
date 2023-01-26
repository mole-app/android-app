package com.mole.android.mole.web.service

sealed interface State<out T> {
    object Loading : State<Nothing>
    data class Error(val throwable: Throwable) : State<Nothing>
    data class Content<T>(val data: T) : State<T>
}