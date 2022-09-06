package com.github.oauth.repositories.barrierauthorise.repository

import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserTokensData
import com.github.oauth.repositories.barrierauthorise.repository.datasource.DataSource

class RepositoryImpl(
    private val dataSource: DataSource<ReceivedUserData, ReceivedUserTokensData>
    ): Repository<ReceivedUserData, ReceivedUserTokensData> {
    override suspend fun createNewUser(userData: String): ReceivedUserData {
        return dataSource.createNewUser(userData)
    }

    override suspend fun authoriseUser(userData: String): ReceivedUserTokensData {
        return dataSource.authoriseUser(userData)
    }
}