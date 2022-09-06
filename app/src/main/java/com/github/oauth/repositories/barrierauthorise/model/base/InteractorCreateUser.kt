package com.github.oauth.repositories.barrierauthorise.model.base

import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData

interface InteractorCreateUser<T> {
    suspend fun createNewUser(inputtedUserData: InputtedUserData): T
}