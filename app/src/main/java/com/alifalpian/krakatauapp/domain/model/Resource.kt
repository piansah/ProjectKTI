package com.alifalpian.krakatauapp.domain.model

sealed class Resource<out R> private constructor() {
    object Idling : Resource<Nothing>()
    object Empty : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val error: String?, val data: T? = null) : Resource<T>()

}