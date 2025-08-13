package com.example.testproject

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.logic.factory.MainViewModelFactory
import com.example.testproject.logic.viewmodel.MainViewModel
import com.example.testproject.navigation.AppNavHost
import com.example.testproject.ui.theme.CFTTestTheme

/**
 * Прочтите README.md в корне проекта
 */

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED)
            startApp() else
            requestCallPermission()

        val factory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.loadFromDataBase()
    }

    private fun requestCallPermission() {
        activityResultLauncher.launch(Manifest.permission.CALL_PHONE)
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startApp()
            } else {
                showAccessDeniedToast()
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

