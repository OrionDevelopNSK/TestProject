package com.example.testproject

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.navigation.AppNavHost
import com.example.testproject.ui.theme.CFTTestTheme
import com.example.testproject.logic.viewmodel.MainViewModel
import com.example.testproject.logic.factory.MainViewModelFactory

/**
 * Прочтите README.md в корне проекта
 */

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResultLauncher.launch(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.CALL_PHONE
            )
        )

        val factory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.loadFromDataBase()
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

