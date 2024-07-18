package com.ysw.presentation.compose

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal inline fun <T> MutableStateFlow<T>.updateUiState(update: (currentState: T) -> T) {
    update { currentState ->
        update(currentState)
    }
}