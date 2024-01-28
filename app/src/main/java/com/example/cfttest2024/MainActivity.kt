package com.example.cfttest2024

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cfttest2024.screen.MainScreen
import com.example.cfttest2024.ui.theme.CFTTest2024Theme
import com.example.cfttest2024.viewmodel.BaseViewModel

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
        viewModel.createDataBaseHelper(this)
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
            CFTTest2024Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }

    private fun showAccessDeniedToast() {
        Toast.makeText(this, getString(R.string.access_denied), Toast.LENGTH_SHORT).show()
    }

}

