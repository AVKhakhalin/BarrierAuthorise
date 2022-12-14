package com.github.oauth.repositories.barrierauthorise.di

import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserTokensData
import com.github.oauth.repositories.barrierauthorise.navigator.AppScreens
import com.github.oauth.repositories.barrierauthorise.navigator.AppScreensImpl
import com.github.oauth.repositories.barrierauthorise.repository.Repository
import com.github.oauth.repositories.barrierauthorise.repository.RepositoryImpl
import com.github.oauth.repositories.barrierauthorise.repository.datasource.RetrofitImpl
import com.github.oauth.repositories.barrierauthorise.repository.settings.Settings
import com.github.oauth.repositories.barrierauthorise.utils.*
import com.github.oauth.repositories.barrierauthorise.utils.network.NetworkStatus
import com.github.oauth.repositories.barrierauthorise.utils.resources.ResourcesProviderImpl
import com.github.oauth.repositories.barrierauthorise.view.activity.MainActivityViewModel
import com.github.oauth.repositories.barrierauthorise.view.fragments.authoriseuser.AuthoriseUserFragmentInteractor
import com.github.oauth.repositories.barrierauthorise.view.fragments.authoriseuser.AuthoriseUserFragmentViewModel
import com.github.oauth.repositories.barrierauthorise.view.fragments.createuser.CreateUserFragmentInteractor
import com.github.oauth.repositories.barrierauthorise.view.fragments.createuser.CreateUserFragmentViewModel
import com.github.oauth.repositories.barrierauthorise.view.fragments.startbuttons.StartButtonsFragmentViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    // Удалённый сервер (API)
    single<Repository<ReceivedUserData, ReceivedUserTokensData>>(named(NAME_REMOTE)) {
        RepositoryImpl(RetrofitImpl())
    }
    // Локальное сохранение данных
    single<Settings> { Settings() }

    // Навигация между окнами
    single<Cicerone<Router>>(named(CICERONE_NAME)) { Cicerone.create() }
    single<NavigatorHolder> {
        get<Cicerone<Router>>(named(CICERONE_NAME)).getNavigatorHolder() }
    single<Router> { get<Cicerone<Router>>(named(CICERONE_NAME)).router }
    single<AppScreens> { AppScreensImpl() }

    // Вспомогательные классы:
    // Определение статуса сети
    single<NetworkStatus> { NetworkStatus(androidContext()) }
    // Получение доступа к ресурсам
    single<ResourcesProviderImpl> { ResourcesProviderImpl(androidContext()) }
}

val screens = module {
    // Scope для MainActivity
    scope(named(MAIN_ACTIVITY_SCOPE)) {
        viewModel {
            MainActivityViewModel()
        }
    }

    // Scope для фрагмента с начальными кнопками выбора действия пользователя
    scope(named(START_BUTTONS_FRAGMENT_SCOPE)) {
        viewModel {
            StartButtonsFragmentViewModel()
        }
    }

    // Scope для фрагмента с регистрацией нового пользователя
    scope(named(CREATE_USER_FRAGMENT_SCOPE)) {
        scoped {
            CreateUserFragmentInteractor(
                get(named(NAME_REMOTE)),
                ResourcesProviderImpl(get()),
                NetworkStatus(get())
            )
        }
        viewModel {
            CreateUserFragmentViewModel(getScope(CREATE_USER_FRAGMENT_SCOPE).get(), get())
        }
    }

    // Scope для фрагмента с регистрацией нового пользователя
    scope(named(AUTHORISE_USER_FRAGMENT_SCOPE)) {
        scoped {
            AuthoriseUserFragmentInteractor(
                get(named(NAME_REMOTE)),
                ResourcesProviderImpl(get()),
                NetworkStatus(get())
            )
        }
        viewModel {
            AuthoriseUserFragmentViewModel(getScope(AUTHORISE_USER_FRAGMENT_SCOPE).get(), get())
        }
    }
}

