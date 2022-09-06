package com.github.oauth.repositories.barrierauthorise.model.base

import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData

interface InteractorAuthoriseUser<T> {
    suspend fun authoriseUser(inputtedUserData: InputtedUserData): T
}