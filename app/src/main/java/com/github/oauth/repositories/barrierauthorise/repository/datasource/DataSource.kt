package com.github.oauth.repositories.barrierauthorise.repository.datasource

interface DataSource<T, D> {
    suspend fun createNewUser(userData: String): T
    suspend fun authoriseUser(userData: String): D
}