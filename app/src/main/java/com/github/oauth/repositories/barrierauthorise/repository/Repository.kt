package com.github.oauth.repositories.barrierauthorise.repository

import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData

interface Repository<T> {
    suspend fun createNewUser(inputtedUserData: InputtedUserData): T
}