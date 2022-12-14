package com.github.oauth.repositories.barrierauthorise.view.fragments.authoriseuser

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.github.oauth.repositories.barrierauthorise.R
import com.github.oauth.repositories.barrierauthorise.databinding.FragmentAuthoriseUserBinding
import com.github.oauth.repositories.barrierauthorise.model.base.BaseFragment
import com.github.oauth.repositories.barrierauthorise.model.data.AppState
import com.github.oauth.repositories.barrierauthorise.model.data.InputtedUserData
import com.github.oauth.repositories.barrierauthorise.repository.settings.Settings
import com.github.oauth.repositories.barrierauthorise.utils.AUTHORISE_USER_FRAGMENT_SCOPE
import com.github.oauth.repositories.barrierauthorise.utils.LOG_TAG
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent

class AuthoriseUserFragment:
    BaseFragment<FragmentAuthoriseUserBinding>(FragmentAuthoriseUserBinding::inflate) {
    /** Задание переменных */ //region
    // ViewModel
    private lateinit var viewModel: AuthoriseUserFragmentViewModel
    // CreateUserFragmentScope
    private lateinit var showAuthoriseUserFragmentScope: Scope
    // Поля для ввода данных о пользователе
    private lateinit var login: EditText
    private lateinit var password: EditText
    // Кнопка для отправки запроса на создание нового пользователя
    private lateinit var authoriseUserButton: Button
    // Класс для сохранения запроса
    private val settings: Settings = KoinJavaComponent.getKoin().get()
    // newInstance для данного класса
    companion object {
        fun newInstance(): AuthoriseUserFragment = AuthoriseUserFragment()
    }
    //endregion

    /** Работа со Scope */ //region
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Задание Scope для данного фрагмента
        showAuthoriseUserFragmentScope = KoinJavaComponent.getKoin().getOrCreateScope(
            AUTHORISE_USER_FRAGMENT_SCOPE, named(AUTHORISE_USER_FRAGMENT_SCOPE)
        )
    }
    override fun onDetach() {
        // Удаление скоупа для данного фрагмента
        showAuthoriseUserFragmentScope.close()
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
        initAuthoriseUserButton()
    }

    // Инициализация ViewModel
    private fun initViewModel() {
        val _viewModel: AuthoriseUserFragmentViewModel by showAuthoriseUserFragmentScope.inject()
        viewModel = _viewModel
        // Подписка на ViewModel
        this.viewModel.subscribe().observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessAuthoriseUser -> {
                // Изменение внешнего вида фрагмента
                binding.scrollLayout.visibility = View.VISIBLE
                binding.progressbar.visibility = View.INVISIBLE
                // Уведомление пользователя о том, что пользователь успешно авторизован
                Toast.makeText(requireActivity(),
                    "${requireContext().getString(R.string.authorise_user_completed)
                    }\n${requireContext().getString(R.string.tokens_show_place)}",
                    Toast.LENGTH_LONG).show()
                Log.d(LOG_TAG, "Токены: \naccess:${appState.data?.tokens?.access}\nrefresh: ${
                    appState.data?.tokens?.refresh}\nexp: ${appState.data?.tokens?.exp}")
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
                Toast.makeText(requireActivity(), appState.error.message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    // Инициализация полей ввода исходной информации и кнопки для отправки запроса
    private fun initFieldsAndSearchButton() {
        login = binding.loginLayoutTextfield
        password = binding.passwordTextfield
    }

    // Инициализация кнопки для отправки запроса
    private fun initAuthoriseUserButton() {
        authoriseUserButton = binding.authoriseUserButton.also {
            it.setOnClickListener {
                val inputtedUserData: InputtedUserData = InputtedUserData()
                inputtedUserData.email = login.text.toString()
                inputtedUserData.password = password.text.toString()
                Toast.makeText(requireActivity(),
                    "${inputtedUserData.email}\n${
                    inputtedUserData.password}", Toast.LENGTH_LONG).show()
                viewModel.authoriseUser(inputtedUserData)
            }
        }
    }
}