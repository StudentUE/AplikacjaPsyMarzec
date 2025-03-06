package com.example.psymarzecv2

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.psymarzecv2.ui.motyw.psy.DogsScreen
import com.example.psymarzecv2.ui.motyw.szczegoly.DogDetailScreen
import com.example.psymarzecv2.ui.motyw.ustawienia.ProfileScreen
import com.example.psymarzecv2.ui.motyw.profil.SettingsScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") {
                DogsScreen(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    navigationController = navController
                )
            }
            composable(
                route = "details/{dogId}",
                arguments = listOf(navArgument("dogId") { type = NavType.IntType })
            ) {
                DogDetailScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
            composable("profile") {
                ProfileScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
            composable("settings") {
                SettingsScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
    }
}
