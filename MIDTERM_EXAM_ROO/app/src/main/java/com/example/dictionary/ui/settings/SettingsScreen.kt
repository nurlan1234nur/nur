package com.example.dictionary.ui.settings

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel
) {
    val displayMode by viewModel.setting.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Тохиргоо") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Үгийн харуулах горим: $displayMode")
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { viewModel.setSetting("english") }) {
                Text("Зөвхөн Англи")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { viewModel.setSetting("mongolian") }) {
                Text("Зөвхөн Монгол")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { viewModel.setSetting("both") }) {
                Text("Англи ба Монгол")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    val context = LocalContext.current
    val viewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(context.applicationContext as Application))
    SettingsScreen(
        navController = rememberNavController(),
        viewModel = viewModel
    )
}
