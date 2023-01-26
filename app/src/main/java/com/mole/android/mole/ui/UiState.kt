package com.mole.android.mole.ui

import com.mole.android.mole.web.service.MoleError

data class UiState<out T>(
    val isLoading: Boolean = false,
    val error: MoleError? = null,
    val isErrorVisible: Boolean = false,
    val content: T? = null,
    val isContentVisible: Boolean = false
)