package com.example.dictionary

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import com.example.dictionary.ui.addedit.AddWordScreen
import com.example.dictionary.ui.main.DictionaryScreen
import com.example.dictionary.ui.settings.SettingsScreen
import com.example.dictionary.ui.theme.DictionaryTheme
import com.example.dictionary.viewmodel.WordViewModel
import com.example.dictionary.viewmodel.WordViewModelFactory
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dictionary.ui.settings.SettingsViewModel
import com.example.dictionary.ui.settings.SettingsViewModelFactory
import java.util.concurrent.TimeUnit
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            setupNotificationWork()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNotificationPermission()
        setContent {
            DictionaryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val wordViewModel: WordViewModel =
                        WordViewModelFactory(application).create(WordViewModel::class.java)
                    val settingsViewModel: SettingsViewModel =
                        SettingsViewModelFactory(application).create(SettingsViewModel::class.java)

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NavHost(navController = navController, startDestination = "dictionary") {
                            composable("dictionary") {
                                DictionaryScreen(navController, wordViewModel)
                            }
                            composable("add_edit_word/{wordId}") { backStackEntry ->
                                val wordId =
                                    backStackEntry.arguments?.getString("wordId")?.toIntOrNull()
                                AddWordScreen(
                                    navController = navController,
                                    wordId = wordId,
                                    wordViewModel = wordViewModel
                                )
                            }
                            composable("settings") {
                                SettingsScreen(navController, settingsViewModel)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    setupNotificationWork()
                }

                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            setupNotificationWork()
        }
    }

    private fun setupNotificationWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val notificationWork = PeriodicWorkRequestBuilder<NotificationService>(
            1, TimeUnit.MINUTES,
            1, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "dictionary_notification",
                ExistingPeriodicWorkPolicy.UPDATE,
                notificationWork
            )
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        val context = LocalContext.current
        val wordViewModel: WordViewModel =
            viewModel(factory = WordViewModelFactory(context.applicationContext as Application))
        val settingsViewModel: SettingsViewModel =
            viewModel(factory = SettingsViewModelFactory(context.applicationContext as Application))
        DictionaryScreen(
            navController = rememberNavController(),
            wordViewModel = wordViewModel,
            settingsViewModel = settingsViewModel
        )
    }
}