package com.github.oauth.repositories.barrierauthorise.navigator

import com.github.oauth.repositories.barrierauthorise.view.fragments.createuser.CreateUserFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AppScreensImpl: AppScreens {
    // Окно с регистрацией нового пользователя
    override fun createUserScreen() = FragmentScreen {
        CreateUserFragment.newInstance()
    }
}