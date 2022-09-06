package com.github.oauth.repositories.barrierauthorise.view.fragments.authoriseuser

import android.util.Base64
import android.widget.Toast
import com.github.oauth.repositories.barrierauthorise.R
import com.github.oauth.repositories.barrierauthorise.model.base.InteractorAuthoriseUser
import com.github.oauth.repositories.barrierauthorise.model.data.AppState
import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserData
import com.github.oauth.repositories.barrierauthorise.model.data.ReceivedUserTokensData
import com.github.oauth.repositories.barrierauthorise.repository.Repository
import com.github.oauth.repositories.barrierauthorise.repository.settings.Settings
import com.github.oauth.repositories.barrierauthorise.utils.network.NetworkStatus
import com.github.oauth.repositories.barrierauthorise.utils.resources.ResourcesProvider
import org.koin.java.KoinJavaComponent
import java.nio.charset.Charset

class AuthoriseUserFragmentInteractor(
    private val remoteRepository: Repository<ReceivedUserData, ReceivedUserTokensData>,
    private val resourcesProviderImpl: ResourcesProvider,
    private val networkStatus: NetworkStatus
): InteractorAuthoriseUser<AppState> {
    /** Исходные данные */ //region
    private val settings: Settings = KoinJavaComponent.getKoin().get()
    //endregion

    override suspend fun authoriseUser(inputtedUserData: InputtedUserData):
            AppState {
        val appState: AppState = if (networkStatus.isOnline()) {
            // Кодирование в Base64 логина и пароля
            val byteEncodedData: ByteArray? = Base64.encode("${inputtedUserData.firstName}:${
                inputtedUserData.password}".toByteArray(Charset.defaultCharset()), Base64.NO_WRAP)
            val stringEncodedData: String? = byteEncodedData?.toString(Charset.defaultCharset())
            AppState.SuccessAuthoriseUser(
                remoteRepository.authoriseUser("Basic ${stringEncodedData ?: ""}"))
        } else {
            Toast.makeText(resourcesProviderImpl.getContext(),
                resourcesProviderImpl.getString(R.string.help_needs_internet_connection),
                Toast.LENGTH_SHORT).show()
            AppState.Error(Throwable(
                resourcesProviderImpl.getString(R.string.error_needs_internet_connection)))
        }
        // Сохранение данных в класс Settings
        (appState as AppState.SuccessAuthoriseUser).data?.let {
            settings.access = it.tokens?.access ?: ""
            settings.refresh = it.tokens?.refresh ?: ""
            settings.exp = it.tokens?.exp ?: -1
        }
        return appState
    }
}