package com.github.oauth.repositories.barrierauthorise.view.fragments.createuser

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.github.oauth.repositories.barrierauthorise.R
import com.github.oauth.repositories.barrierauthorise.databinding.FragmentCreateUserBinding
import com.github.oauth.repositories.barrierauthorise.model.base.BaseFragment
import com.github.oauth.repositories.barrierauthorise.model.data.AppState
import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData
import com.github.oauth.repositories.barrierauthorise.repository.settings.Settings
import com.github.oauth.repositories.barrierauthorise.utils.CREATE_USER_FRAGMENT_SCOPE
import com.github.oauth.repositories.barrierauthorise.utils.LOG_TAG
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent


class CreateUserFragment:
    BaseFragment<FragmentCreateUserBinding>(FragmentCreateUserBinding::inflate) {
    /** Задание переменных */ //region
    // ViewModel
    private lateinit var viewModel: CreateUserFragmentViewModel
    // CreateUserFragmentScope
    private lateinit var showCreateUserFragmentScope: Scope
    // Поля для ввода данных о пользователе
    private lateinit var firstName: EditText
    private lateinit var email: EditText
    private lateinit var isAgreed: CheckBox
    private lateinit var password: EditText
    // Кнопка для отправки запроса на создание нового пользователя
    private lateinit var createUserButton: Button
    // Класс для сохранения запроса
    private val settings: Settings = KoinJavaComponent.getKoin().get()
    // newInstance для данного класса
    companion object {
        fun newInstance(): CreateUserFragment = CreateUserFragment()
    }
    //endregion

    /** Работа со Scope */ //region
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Задание Scope для данного фрагмента
        showCreateUserFragmentScope = KoinJavaComponent.getKoin().getOrCreateScope(
            CREATE_USER_FRAGMENT_SCOPE, named(CREATE_USER_FRAGMENT_SCOPE)
        )
    }
    override fun onDetach() {
        // Удаление скоупа для данного фрагмента
        showCreateUserFragmentScope.close()
        super.onDetach()
    }
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        initViewModel()
        // Инициализация полей ввода исходной информации и кнопки для отправки запроса
        initFieldsAndSearchButton()
        // Инициализация кнопки для отправки запроса
        initCreateUserButton()
    }

    // Инициализация ViewModel
    private fun initViewModel() {
        val _viewModel: CreateUserFragmentViewModel by showCreateUserFragmentScope.inject()
        viewModel = _viewModel
        // Подписка на ViewModel
        this.viewModel.subscribe().observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessCreateNewUser -> {
                // Изменение внешнего вида фрагмента
                binding.scrollLayout.visibility = View.VISIBLE
                binding.progressbar.visibility = View.INVISIBLE
                // Уведомление пользователя о том, что новый пользователь успешно создан
                Toast.makeText(requireActivity(),
                    requireActivity().getString(R.string.new_user_created), Toast.LENGTH_LONG).show()
                Log.d(LOG_TAG, "Новый пользователь создан:\n" +
                    "user_id: ${appState.data?.user?.userId}\n" +
                    "client_id: ${appState.data?.user?.clientId}\n" +
                    "role_id: ${appState.data?.user?.roleId}\n" +
                    "check_in: ${appState.data?.user?.checkIn}\n" +
                    "surname: ${appState.data?.user?.surname}\n" +
                    "first_name: ${appState.data?.user?.firstName}\n" +
                    "patronymic: ${appState.data?.user?.patronymic}\n" +
                    "birth_date: ${appState.data?.user?.birthDate}\n" +
                    "gender: ${appState.data?.user?.gender}\n" +
                    "welcome: ${appState.data?.user?.welcome}\n" +
                    "email: ${appState.data?.user?.email}\n" +
                    "mobile: ${appState.data?.user?.mobile}\n" +
                    "uuid: ${appState.data?.user?.uuid}\n" +
                    "is_offer: ${appState.data?.user?.isOffer}\n" +
                    "is_enabled: ${appState.data?.user?.isEnabled}\n" +
                    "is_agreed: ${appState.data?.user?.isAgreed}\n" +
                    "last_activity: ${appState.data?.user?.lastActivity}\n" +
                    "created_at: ${appState.data?.user?.createdAt}\n" +
                    "update_at: ${appState.data?.user?.updateAt}\n" +
                    "password: ${settings.password}\n")
            }
            is AppState.Loading -> {
                // Изменение внешнего вида фрагмента
                binding.scrollLayout.visibility = View.INVISIBLE
                binding.progressbar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                // Изменение внешнего вида фрагмента
                binding.scrollLayout.visibility = View.VISIBLE
                binding.progressbar.visibility = View.INVISIBLE
                // Уведомление пользователя об ошибке
                Toast.makeText(requireActivity(), "${appState.error.message}",
                    Toast.LENGTH_LONG).show()
                Log.d(LOG_TAG, "ОШИБКА: ${appState.error.message}")
            }
            else -> {
                Toast.makeText(requireActivity(), "Else", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Инициализация полей ввода исходной информации и кнопки для отправки запроса
    private fun initFieldsAndSearchButton() {
        firstName = binding.firstNameLayoutTextfield
        email = binding.emailTextfield
        isAgreed = binding.isAgreedCheckbox
        password = binding.passwordTextfield
    }

    // Инициализация кнопки для отправки запроса
    private fun initCreateUserButton() {
        createUserButton = binding.createNewUserButton.also {
            it.setOnClickListener {
                val inputtedUserData: InputtedUserData = InputtedUserData()
                inputtedUserData.first_name = firstName.text.toString()
                inputtedUserData.email = email.text.toString()
                inputtedUserData.is_agreed = isAgreed.isChecked
                inputtedUserData.password = password.text.toString()
                Toast.makeText(requireActivity(), "${inputtedUserData.first_name}\n${
                    inputtedUserData.email}\n${inputtedUserData.is_agreed}\n${
                        inputtedUserData.password}", Toast.LENGTH_LONG).show()
                viewModel.createNewUser(inputtedUserData)
            }
        }
    }
}