package com.example.psymarzecv2.ui.motyw.profil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.psymarzecv2.ui.motyw.TopBar

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Ustawienia",
            canNavigateBack = true,
            onNavigateBack = onNavigateBack
        )
    }
}

