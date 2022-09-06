package com.github.oauth.repositories.barrierauthorise.view.fragments.createuser

import androidx.lifecycle.LiveData
import com.github.oauth.repositories.barrierauthorise.model.base.BaseViewModel
import com.github.oauth.repositories.barrierauthorise.model.data.AppState
import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData
import com.github.oauth.repositories.barrierauthorise.repository.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateUserFragmentViewModel(
    private val interactor: CreateUserFragmentInteractor,
    private val settings: Settings
): BaseViewModel<AppState>() {
    /** Задание исходных данных */ //region
    // Информация с результатом запроса
    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData
    //endregion

    fun createNewUser(inputtedUserData: InputtedUserData) {
        // Сохранение данных о пользователей в класс Settings
        settings.firstName = inputtedUserData.firstName
        settings.email = inputtedUserData.email
        settings.isAgreed = inputtedUserData.isAgreed
        settings.password = inputtedUserData.password

        // Регистрация нового пользователя
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(inputtedUserData)
        }
    }

    private suspend fun startInteractor(inputtedUserData: InputtedUserData) =
        withContext(Dispatchers.IO) {
            _mutableLiveData.postValue(interactor.createNewUser(inputtedUserData))
        }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }
}