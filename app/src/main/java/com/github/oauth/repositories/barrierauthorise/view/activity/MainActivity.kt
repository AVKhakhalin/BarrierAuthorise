package com.github.oauth.repositories.barrierauthorise.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.github.oauth.repositories.barrierauthorise.R
import com.github.oauth.repositories.barrierauthorise.databinding.ActivityMainBinding
import com.github.oauth.repositories.barrierauthorise.navigator.BackButtonListener
import com.github.oauth.repositories.barrierauthorise.utils.MAIN_ACTIVITY_SCOPE
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent

class MainActivity: AppCompatActivity() {
    /** Задание исходных данных */ //region
    // Binding
    private lateinit var binding: ActivityMainBinding
    // MainActivityScope
    private val mainActivityScope: Scope = KoinJavaComponent.getKoin().getOrCreateScope(
        MAIN_ACTIVITY_SCOPE, named(MAIN_ACTIVITY_SCOPE)
    )
    // ViewModel
    private lateinit var viewModel: MainActivityViewModel
    // Навигация
    private val navigator = AppNavigator(this@MainActivity, R.id.fragments_container)
    private val navigatorHolder: NavigatorHolder = KoinJavaComponent.getKoin().get()
    // Кнопка перехода на окна с регистрацией нового пользователя
    private lateinit var createUserButton: Button
    // Кнопка перехода на окна с аутентификацией существующего пользователя
    private lateinit var authoriseUserButton: Button
    //endregion

    override fun onDestroy() {
        // Удаление скоупа для активити
        mainActivityScope.close()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Подключение Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Инициализация ViewModel
        initViewModel()
        // Инициализация кнопок
        initButtons()
        // Отслеживание первого или последующего запусков MainActivity
        if (savedInstanceState != null) {
            // Установка текущего экрана приложения
            navigatorHolder.setNavigator(navigator)
        }
        // Отображение содержимого окна
        setContentView(binding.root)
    }

    // Инициализация ViewModel
    private fun initViewModel() {
        // Начальная установка ViewModel
        val viewModel: MainActivityViewModel by mainActivityScope.inject()
        this.viewModel = viewModel
    }

    /** Методы для настройки навигатора */ //region
    override fun onResume() {
        super.onResume()
        // Установка навигатора
        navigatorHolder.setNavigator(navigator)
    }
    override fun onPause() {
        super.onPause()
        // Удаление навигатора
        navigatorHolder.removeNavigator()
    }
    override fun onBackPressed() {
//        // Закрыть активити, если в настоящий момент открыт только один фрагмент
//        if (supportFragmentManager.fragments.size == 1) finish()
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        viewModel.router.exit()
    }
    //endregion

    // Инициализация кнопок
    private fun initButtons() {
        createUserButton = binding.createNewUser.also {
            it.setOnClickListener {
                viewModel.router.navigateTo(viewModel.screens.createUserScreen())
            }
        }
        authoriseUserButton = binding.authoriseUser
    }
}