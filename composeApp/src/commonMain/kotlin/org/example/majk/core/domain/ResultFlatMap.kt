package org.example.majk.core.domain

suspend fun <T> Result<T>.flatMap(block: suspend (T) -> Unit): Result<Unit> =
    fold(
        onSuccess = {
            runCatching { block(it) }
        },
        onFailure = {
            Result.failure(it)
        }
    )