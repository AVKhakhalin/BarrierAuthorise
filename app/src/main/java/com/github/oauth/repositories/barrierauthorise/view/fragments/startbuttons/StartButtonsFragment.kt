package com.github.oauth.repositories.barrierauthorise.view.fragments.startbuttons

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.github.oauth.repositories.barrierauthorise.databinding.FragmentStartButtonsBinding
import com.github.oauth.repositories.barrierauthorise.model.base.BaseFragment
import com.github.oauth.repositories.barrierauthorise.utils.START_BUTTONS_FRAGMENT_SCOPE
import com.github.oauth.repositories.barrierauthorise.view.activity.MainActivity
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent

class StartButtonsFragment:
    BaseFragment<FragmentStartButtonsBinding>(FragmentStartButtonsBinding::inflate) {
    /** Исходные данные */ //region
    // ViewModel
    private lateinit var viewModel: StartButtonsFragmentViewModel
    // CreateUserFragmentScope
    private lateinit var startButtonsFragmentScope: Scope
    // Кнопка перехода на окна с регистрацией нового пользователя
    private lateinit var createUserButton: Button
    // Кнопка перехода на окна с аутентификацией существующего пользователя
    private lateinit var authoriseUserButton: Button
    // newInstance для данного класса
    companion object {
        fun newInstance(): StartButtonsFragment = StartButtonsFragment()
    }
    //endregion

    /** Работа со Scope */ //region
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Задание Scope для данного фрагмента
        startButtonsFragmentScope = KoinJavaComponent.getKoin().getOrCreateScope(
            START_BUTTONS_FRAGMENT_SCOPE, named(START_BUTTONS_FRAGMENT_SCOPE)
        )
    }
    override fun onDetach() {
        // Удаление скоупа для данного фрагмента
        startButtonsFragmentScope.close()
        super.onDetach()
    }
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Инициализация кнопок
        initButtons()
        // Инициализация ViewModel
        initViewModel()
    }

    // Инициализация ViewModel
    private fun initViewModel() {
        // Начальная установка ViewModel
        val viewModel: StartButtonsFragmentViewModel by startButtonsFragmentScope.inject()
        this.viewModel = viewModel
    }

    // Инициализация кнопок
    private fun initButtons() {
        createUserButton = binding.createNewUser.also {
            it.setOnClickListener {
                viewModel.router.navigateTo(viewModel.screens.createUserScreen())
                (requireActivity() as MainActivity).setIsOnlyOneFragmentExist(false)
            }
        }
        authoriseUserButton = binding.authoriseUser.also {
            it.setOnClickListener {
                viewModel.router.navigateTo(viewModel.screens.authoriseUserScreen())
                (requireActivity() as MainActivity).setIsOnlyOneFragmentExist(false)
            }
        }
    }
}