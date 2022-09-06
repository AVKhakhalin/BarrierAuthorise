package com.github.oauth.repositories.barrierauthorise.navigator

import com.github.oauth.repositories.barrierauthorise.view.fragments.authoriseuser.AuthoriseUserFragment
import com.github.oauth.repositories.barrierauthorise.view.fragments.createuser.CreateUserFragment
import com.github.oauth.repositories.barrierauthorise.view.fragments.startbuttons.StartButtonsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AppScreensImpl: AppScreens {
    // Окно с начальными кнопками выбора пользователя
    override fun startButtonsScreen() = FragmentScreen {
        StartButtonsFragment.newInstance()
    }

    // Окно с регистрацией нового пользователя
    override fun createUserScreen() = FragmentScreen {
        CreateUserFragment.newInstance()
    }

    // Окно с авторизацией пользователя
    override fun authoriseUserScreen() = FragmentScreen {
        AuthoriseUserFragment.newInstance()
    }
}