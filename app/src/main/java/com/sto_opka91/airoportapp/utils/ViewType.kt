package com.sto_opka91.airoportapp.utils

sealed interface ViewType {

    object Loading : ViewType

    object Loaded : ViewType

    data class Error(val description: String) : ViewType
}

interface ViewState {

    val viewType: ViewType
}