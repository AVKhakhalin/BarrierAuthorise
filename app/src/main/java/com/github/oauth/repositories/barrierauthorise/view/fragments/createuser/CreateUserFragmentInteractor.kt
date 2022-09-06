package com.github.oauth.repositories.barrierauthorise.view.fragments.createuser

import android.widget.Toast
import com.github.oauth.repositories.barrierauthorise.R
import com.github.oauth.repositories.barrierauthorise.model.base.InteractorCreateUser
import com.github.oauth.repositories.barrierauthorise.model.data.AppState
import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserTokensData
import com.github.oauth.repositories.barrierauthorise.repository.Repository
import com.github.oauth.repositories.barrierauthorise.repository.settings.Settings
import com.github.oauth.repositories.barrierauthorise.utils.network.NetworkStatus
import com.github.oauth.repositories.barrierauthorise.utils.resources.ResourcesProvider
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
            // Конвертирование данных в JSON формат
            val userData = HashMap<String, String>()
            userData["\"first_name\""] = " \"" + inputtedUserData.firstName + "\""
            userData["\"email\""] =  " \"" + inputtedUserData.email + "\""
            userData["\"is_agreed\""] = " " + inputtedUserData.isAgreed
            userData["\"password\""] = " \"" + inputtedUserData.password + "\""
            val correctedUserDataMapToJson: String =
                "$userData".replace("\"= \"", "\": \"")
            AppState.SuccessCreateNewUser(
                remoteRepository.createNewUser(correctedUserDataMapToJson))
        } else {
            Toast.makeText(resourcesProviderImpl.getContext(),
                resourcesProviderImpl.getString(R.string.help_needs_internet_connection),
                Toast.LENGTH_SHORT).show()
            AppState.Error(Throwable(
                resourcesProviderImpl.getString(R.string.error_needs_internet_connection)))
        }
        // Сохранение данных в класс Settings
        (appState as AppState.SuccessCreateNewUser).data?.let {
            settings.birthDate = it.birthDate ?: ""
            settings.checkIn = it.checkIn ?: ""
            settings.email = it.email
            settings.clientId = it.clientId
            settings.gender = it.gender ?: ""
            settings.createdAt = it.createdAt ?: ""
            settings.firstName = it.firstName
            settings.isAgreed = it.isAgreed
            settings.isEnabled = it.isEnabled
            settings.isOffer = it.isOffer
            settings.lastActivity = it.lastActivity ?: ""
            settings.mobile = it.mobile ?: ""
            settings.patronymic = it.patronymic ?: ""
            settings.roleId = it.roleId
            settings.surname = it.surname ?: ""
            settings.updateAt = it.updateAt ?: ""
            settings.userId = it.userId
            settings.uuid = it.uuid ?: ""
            settings.welcome = it.welcome ?: ""
        }
        return appState
    }
}