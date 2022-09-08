package com.github.oauth.repositories.barrierauthorise.view.fragments.authoriseuser

import androidx.lifecycle.LiveData
import com.github.oauth.repositories.barrierauthorise.model.base.BaseViewModel
import com.github.oauth.repositories.barrierauthorise.model.data.AppState
import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData
import com.github.oauth.repositories.barrierauthorise.repository.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthoriseUserFragmentViewModel(
    private val interactor: AuthoriseUserFragmentInteractor,
    private val settings: Settings
    ): BaseViewModel<AppState>() {
    /** Задание исходных данных */ //region
    // Информация с результатом запроса
    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData
    //endregion

    fun authoriseUser(inputtedUserData: InputtedUserData) {
        // Сохранение данных об авторизовывающемся пользователе в класс Settings
        settings.firstName = inputtedUserData.first_name.toString()
        settings.email = inputtedUserData.email.toString()
        settings.password = inputtedUserData.password.toString()

        // Авторизация пользователя
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(inputtedUserData)
        }
    }

    private suspend fun startInteractor(inputtedUserData: InputtedUserData) =
        withContext(Dispatchers.IO) {
            _mutableLiveData.postValue(interactor.authoriseUser(inputtedUserData))
        }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }
}