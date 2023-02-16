package com.mole.android.mole.ui

import com.mole.android.mole.web.service.MoleError

open class UiState<out T>(
    val isLoading: Boolean,
    val isErrorVisible: Boolean,
    val isContentVisible: Boolean,
    val error: MoleError? = null,
    val content: T? = null
)

class Loading<out T> : UiState<T>(
    isLoading = true,
    isErrorVisible = false,
    isContentVisible = true
)

class Error<out T>(error: MoleError) : UiState<T>(
    isLoading = false,
    isErrorVisible = true,
    isContentVisible = false,
    error = error
)

class Content<out T>(data: T) : UiState<T>(
    isLoading = false,
    isErrorVisible = false,
    isContentVisible = true,
    content = data
)