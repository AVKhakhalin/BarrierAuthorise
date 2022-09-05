package com.github.oauth.repositories.barrierauthorise.repository

import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserData
import com.github.oauth.repositories.barrierauthorise.repository.datasource.DataSource

class RepositoryImpl(
    private val dataSource: DataSource<ReceivedUserData>
): Repository<ReceivedUserData> {
    override suspend fun createNewUser(inputtedUserData: InputtedUserData): ReceivedUserData {
        return dataSource.createNewUser(inputtedUserData)
    }
}