package com.sto_opka91.airoportapp.utils

sealed class Resource<out R> {
    data class Success<out R>(val data: R) : Resource<R>()
    data class UnSuccess<out R>(val data: R) : Resource<R>()
    data class Failure<out R>(val exception: Exception, val data: R? = null) : Resource<R>()
}