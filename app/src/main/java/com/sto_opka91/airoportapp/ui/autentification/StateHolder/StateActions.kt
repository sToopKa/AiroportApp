package com.sto_opka91.airoportapp.ui.autentification.StateHolder

interface StateActions {
    data object NavigateToMainEvent : StateActions
    data object NavigateToLoginFragmentEvent : StateActions

    data class ChangeLoginEvent(val login: String) : StateActions
    data class ChangePasswordEvent(val password: String) : StateActions

    data class ShowErrorEvent(val text: String) : StateActions
}
