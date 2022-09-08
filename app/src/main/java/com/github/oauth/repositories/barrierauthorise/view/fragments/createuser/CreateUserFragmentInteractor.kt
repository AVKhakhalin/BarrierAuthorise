package com.github.oauth.repositories.barrierauthorise.view.fragments.createuser

import android.util.Log
import android.widget.Toast
import com.github.oauth.repositories.barrierauthorise.R
import com.github.oauth.repositories.barrierauthorise.model.base.InteractorCreateUser
import com.github.oauth.repositories.barrierauthorise.model.data.AppState
import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserTokensData
import com.github.oauth.repositories.barrierauthorise.repository.Repository
import com.github.oauth.repositories.barrierauthorise.repository.settings.Settings
import com.github.oauth.repositories.barrierauthorise.utils.LOG_TAG
import com.github.oauth.repositories.barrierauthorise.utils.network.NetworkStatus
import com.github.oauth.repositories.barrierauthorise.utils.resources.ResourcesProvider
import com.google.gson.Gson
import org.koin.java.KoinJavaComponent

class CreateUserFragmentInteractor(
    private val remoteRepository: Repository<ReceivedUserData, ReceivedUserTokensData>,
    private val resourcesProviderImpl: ResourcesProvider,
    private val networkStatus: NetworkStatus
): InteractorCreateUser<AppState> {
    /** Исходные данные */ //region
    private val settings: Settings = KoinJavaComponent.getKoin().get()
    //endregion

    override suspend fun createNewUser(inputtedUserData: InputtedUserData):
            AppState {
        val appState: AppState = if (networkStatus.isOnline()) {
            AppState.SuccessCreateNewUser(
                remoteRepository.createNewUser(inputtedUserData))
        } else {
            Toast.makeText(resourcesProviderImpl.getContext(),
                resourcesProviderImpl.getString(R.string.help_needs_internet_connection),
                Toast.LENGTH_SHORT).show()
            AppState.Error(Throwable(
                resourcesProviderImpl.getString(R.string.error_needs_internet_connection)))
        }
        // Сохранение данных в класс Settings
        (appState as AppState.SuccessCreateNewUser).data?.let {
            it.user?.let { userData ->
                settings.birthDate = userData.birthDate ?: ""
                settings.checkIn = userData.checkIn ?: ""
                settings.email = userData.email
                settings.clientId = userData.clientId
                settings.gender = userData.gender ?: ""
                settings.createdAt = userData.createdAt ?: ""
                settings.firstName = userData.firstName
                settings.isAgreed = userData.isAgreed == 1
                settings.isEnabled = userData.isEnabled == 1
                settings.isOffer = userData.isOffer == 1
                settings.lastActivity = userData.lastActivity ?: ""
                settings.mobile = userData.mobile ?: ""
                settings.patronymic = userData.patronymic ?: ""
                settings.roleId = userData.roleId
                settings.surname = userData.surname ?: ""
                settings.updateAt = userData.updateAt ?: ""
                settings.userId = userData.userId
                settings.uuid = userData.uuid ?: ""
                settings.welcome = userData.welcome ?: ""
            }
        }
        return appState
    }
}