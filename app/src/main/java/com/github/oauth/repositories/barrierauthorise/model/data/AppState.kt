package com.github.oauth.repositories.barrierauthorise.model.data

sealed class AppState {
    data class SuccessCreateNewUser(val data: ReceivedUserData?): AppState()
    data class Error(val error: Throwable): AppState()
    data class Loading(val progress: Int?): AppState()
}