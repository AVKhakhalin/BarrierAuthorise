package com.github.oauth.repositories.barrierauthorise.repository.datasource

import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData

interface DataSource<T> {
    suspend fun createNewUser(inputtedUserData: InputtedUserData): T
}