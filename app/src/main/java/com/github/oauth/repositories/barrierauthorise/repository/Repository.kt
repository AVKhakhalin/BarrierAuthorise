package com.github.oauth.repositories.barrierauthorise.repository

import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData


interface Repository<T, D> {
    suspend fun createNewUser(userData: InputtedUserData): T
    suspend fun authoriseUser(userData: String): D
}