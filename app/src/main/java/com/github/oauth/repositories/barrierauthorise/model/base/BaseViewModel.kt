package com.github.oauth.repositories.barrierauthorise.model.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.oauth.repositories.barrierauthorise.model.data.AppState
import com.github.oauth.repositories.barrierauthorise.navigator.AppScreens
import com.github.oauth.repositories.barrierauthorise.repository.settings.Settings
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent

abstract class BaseViewModel<T: AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData(),
    protected open val _mutableLiveDataSettings: MutableLiveData<Settings> = MutableLiveData()
): ViewModel() {
    /** Исходные данные */ //region
    // Навигация
    val screens: AppScreens = KoinJavaComponent.getKoin().get()
    val router: Router = KoinJavaComponent.getKoin().get()
    //endregion

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun handleError(error: Throwable)
}