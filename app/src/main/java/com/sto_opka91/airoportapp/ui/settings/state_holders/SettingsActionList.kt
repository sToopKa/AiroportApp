package com.sto_opka91.airoportapp.ui.settings.state_holders

interface SettingsActionList {
    data class UpdatePhoto(val uri: String) : SettingsActionList
    data object PhotoPickError : SettingsActionList
    data object GoToLogin : SettingsActionList

    data object GoToPrivateInfo : SettingsActionList
    data object UpdateBirthDate : SettingsActionList

    data class ShowError(val message: String) : SettingsActionList


}