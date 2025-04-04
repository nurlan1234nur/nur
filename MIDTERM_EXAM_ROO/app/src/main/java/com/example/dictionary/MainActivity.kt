package com.example.dictionary

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dictionary.ui.main.DictionaryScreen
import com.example.dictionary.ui.settings.SettingsScreen
import com.example.dictionary.ui.theme.DictionaryTheme
import com.example.dictionary.viewmodel.WordViewModel
import com.example.dictionary.viewmodel.WordViewModelFactory
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dictionary.ui.add_edit_word.AddWordScreen
import com.example.dictionary.ui.settings.SettingsViewModel
import com.example.dictionary.ui.settings.SettingsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val wordViewModel: WordViewModel = WordViewModelFactory(application).create(WordViewModel::class.java)
                    val settingsViewModel: SettingsViewModel = SettingsViewModelFactory(application).create(SettingsViewModel::class.java)

                    NavHost(navController = navController, startDestination = "dictionary") {
                        composable("dictionary") {
                            DictionaryScreen(navController, wordViewModel)
                        }
                        composable("add_edit_word/{wordId}") { backStackEntry ->
                            val wordId = backStackEntry.arguments?.getString("wordId")?.toIntOrNull()
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    val wordViewModel: WordViewModel = viewModel(factory = WordViewModelFactory(context.applicationContext as Application))
    val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(context.applicationContext as Application))
    DictionaryScreen(
        navController = rememberNavController(),
        wordViewModel = wordViewModel,
        settingsViewModel = settingsViewModel
    )
}