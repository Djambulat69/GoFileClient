package com.djambulat69.gofileclient.utils

sealed class Data<T>(open val data: T? = null) {
    class Loading<T> : Data<T>()
    class Success<T>(override val data: T) : Data<T>(data)
    class Failure<T>(val message: String) : Data<T>()
}

inline fun <T> Data<T>.process(
    onLoading: () -> Unit,
    onSuccess: (T) -> Unit,
    onFailure: () -> Unit
) {
    when (this) {
        is Data.Loading -> onLoading()
        is Data.Success -> onSuccess(this.data)
        is Data.Failure -> onFailure()
    }
}
