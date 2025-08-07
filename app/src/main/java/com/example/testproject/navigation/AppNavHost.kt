package com.example.testproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testproject.screen.DetailScreen
import com.example.testproject.screen.MainScreen
import com.example.testproject.viewmodel.BaseViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "mainScreen",
    viewModel: BaseViewModel
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("mainScreen") {
            MainScreen(
                viewModel = viewModel,
                onNavigateToDetailScreen = { navController.navigate("detailScreen") }
            )
        }
        composable("detailScreen") {
            DetailScreen(
                viewModel = viewModel,
                onNavigateToMainScreen = {navController.popBackStack()})
        }
    }
}
