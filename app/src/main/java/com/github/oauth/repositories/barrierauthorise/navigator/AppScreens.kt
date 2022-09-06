package com.github.oauth.repositories.barrierauthorise.navigator

import com.github.terrakok.cicerone.androidx.FragmentScreen

interface AppScreens {
    fun startButtonsScreen(): FragmentScreen
    fun createUserScreen(): FragmentScreen
    fun authoriseUserScreen(): FragmentScreen
}