package com.github.oauth.repositories.barrierauthorise.di

import com.github.oauth.repositories.barrierauthorise.navigator.AppScreens
import com.github.oauth.repositories.barrierauthorise.navigator.AppScreensImpl
import com.github.oauth.repositories.barrierauthorise.utils.CICERONE_NAME
import com.github.oauth.repositories.barrierauthorise.utils.network.NetworkStatus
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    // Навигация между окнами
    single<Cicerone<Router>>(named(CICERONE_NAME)) { Cicerone.create() }
    single<NavigatorHolder> {
        get<Cicerone<Router>>(named(CICERONE_NAME)).getNavigatorHolder() }
    single<Router> { get<Cicerone<Router>>(named(CICERONE_NAME)).router }
    single<AppScreens> { AppScreensImpl() }

    // Вспомогательные классы:
    // Определение статуса сети
    single<NetworkStatus> { NetworkStatus(androidContext()) }
}

val screens = module {

}

