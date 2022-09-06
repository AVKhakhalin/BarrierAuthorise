package com.github.oauth.repositories.barrierauthorise.repository


interface Repository<T, D> {
    suspend fun createNewUser(userData: String): T
    suspend fun authoriseUser(userData: String): D
}