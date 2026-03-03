package francisco.simon.projectkmp.features.login.screen

sealed interface LoginUiEvent {
    object LoginSuccessEvent : LoginUiEvent
}
