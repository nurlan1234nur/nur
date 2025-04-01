package com.example.dictionary.ui.Settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dictionary.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = remember { SettingsViewModel() }, navController: NavController) {
    // collectAsState() нь StateFlow-ийг удирдана
    val displayMode by viewModel.setting.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Тохиргоо", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Үгийн харуулах горим: $displayMode")
        Spacer(modifier = Modifier.height(8.dp))

        // Сонголтуудын товчнууд
        Button(onClick = {
            viewModel.setSetting("english")
        }) {
            Text("Зөвхөн Англи")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.setSetting("mongolian")
        }) {
            Text("Зөвхөн Монгол")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.setSetting("both")
        }) {
            Text("Англи ба Монгол")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Буцах товч
        Button(onClick = {
            navController.popBackStack()  // Буцах товч дарахад өмнөх хуудас руу буцах
        }) {
            Text("Буцах")
        }
    }
}

@Preview
@Composable
fun PreviewSettings() {
    SettingsScreen(viewModel = SettingsViewModel(), navController = rememberNavController())
}
