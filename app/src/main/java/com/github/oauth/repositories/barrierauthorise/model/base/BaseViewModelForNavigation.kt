package com.github.oauth.repositories.barrierauthorise.model.base

import androidx.lifecycle.ViewModel
import com.github.oauth.repositories.barrierauthorise.navigator.AppScreens
import com.github.terrakok.cicerone.Router
import org.koin.java.KoinJavaComponent

abstract class BaseViewModelForNavigation: ViewModel() {
    /** Исходные данные */ //region
    // Навигация
    val screens: AppScreens = KoinJavaComponent.getKoin().get()
    val router: Router = KoinJavaComponent.getKoin().get()
    //endregion
}