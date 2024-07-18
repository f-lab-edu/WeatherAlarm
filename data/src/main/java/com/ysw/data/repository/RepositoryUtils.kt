package com.ysw.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal suspend fun <T> runWithDispatcher(
    coroutineDispatcher: CoroutineDispatcher,
    runFunction: suspend () -> T
): T =
    withContext(coroutineDispatcher) {
        runFunction()
    }