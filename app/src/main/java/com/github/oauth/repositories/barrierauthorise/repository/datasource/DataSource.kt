package com.github.oauth.repositories.barrierauthorise.repository.datasource

import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData

interface DataSource<T, D> {
    suspend fun createNewUser(userData: InputtedUserData): T
    suspend fun authoriseUser(userData: String): D
}