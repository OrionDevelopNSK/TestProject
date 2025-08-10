package com.example.testproject

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.testproject.navigation.AppNavHost
import com.example.testproject.ui.theme.CFTTestTheme
import com.example.testproject.viewmodel.BaseViewModel


/**
PS. В ТЗ не написано о запрете использовании библиотеки Jetpack compose, а он в корне меняет всю разработку приложения:
- отстутствие Fragments, View, разметки XML, обратных вызовов для кнопок и т.д.


Техническое задание:

1. Выводится краткая информация о пользователях (ФИО, фотография, адрес, номер телефона) в виде списка, полученная от https://randomuser.me ;

- JSON получается с помощью Retrofit2 и обрабатывается благодаря Moshi

2. По клику на элемент списка на отдельном экране показывается полная информация о выбранном пользователе;

- навигация обеспечивается библиотеке Navigation compose;

3. Данные о пользователях не теряются при перезапуске приложения;

- функционал обеспечивается благодаря библиотеке Room;
4. Нажатие на Email, номер телефона, адрес/координаты отправляет пользователя в приложение, которое может обработать эти данные (почта, звонилка, карты);

- функционал обеспечивается с помощью Intents
5. Список пользователей можно обновить принудительно;

- (!не понятно что здесь имеется ввиду). Мое решение: при нажатии кнопки "Clear users list" происходит очистка базы данных пользователей , и происходит перересовка списка Users на MainScreen;

6. Пользователю выводятся уведомления о возникших ошибках при загрузке данных или работе с ними.

- при отстутствии сети интернет или не получении JSON с сервера, пользователь уведомляется о возникших проблемах.


Используемые библиотеки:

Jetpack compose.
Причины выбора:
Требуется написание меньше кода, быстродействие.
Код пишется только на Kotlin, полное отсутствие XML разметки.
Полная поддержка Navigation, ViewModel, Livedata.
Красивые приложения благодаря Material Design, темной темы, анимации и многого другого.
Возможность предварительного просмотра макета прямо в IDE.
Продвигается Google как новый стандарт написания приложений.

Retrofit2.
Аналоги: Volley
Причины выбора:
Retrofit быстрее, понятное API, легко сочетается с JSON библиотеками Moshi, Gson, Jackson

Room
Аналоги: Realm
Причины выбора:
Высокая производительность, понятное API, поддержка Google


Coil
Аналоги: Fresco, Picasso, Glide
Преимущества:
Написана на Kotlin, использует Coroutine, быстрее Glide, понятное API.

Moshi
Аналоги: Gson, Jackson (самые популярные)
Причины выбора:
Самый новый из перечисленных, Moshi предлагает более сжатый API, чем библиотеки вроде Jackson или Gson, написана в основном на Kotlin, занимает меньше места и быстрее аналогов. Имеет хорошую поддержку.

Все эти библиотеки помогают избежать написания большого количества шаблонного кода, сокращают время разработки и сокращают вероятность допущения ошибок.
*/

class MainActivity : ComponentActivity() {
    private val viewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResultLauncher.launch(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.CALL_PHONE
            )
        )
        viewModel.createDataBaseHelper(applicationContext)
        viewModel.loadInfoFromDatabase()
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var isGranted = false
            permissions.entries.forEach {
                isGranted = it.value
            }
            when {
                isGranted -> startApp()
                else -> showAccessDeniedToast()
            }
        }

    private fun startApp() {
        setContent {
            CFTTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(viewModel = viewModel)
                }
            }
        }
    }

    private fun showAccessDeniedToast() {
        Toast.makeText(this, getString(R.string.access_denied), Toast.LENGTH_SHORT).show()
    }

}

