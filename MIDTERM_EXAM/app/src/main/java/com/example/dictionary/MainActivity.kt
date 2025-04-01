package com.example.dictionary.ui

import DictionaryScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.*
import com.example.dictionary.viewmodel.WordViewModel
import com.example.dictionary.viewmodel.WordViewModelFactory
import com.example.dictionary.data.WordRepository
import com.example.dictionary.ui.AddWords.AddWordScreen
import com.example.dictionary.ui.Settings.SettingsScreen
import com.example.dictionary.ui.theme.DictionaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val wordRepository = WordRepository(this)
        val wordViewModel = ViewModelProvider(this, WordViewModelFactory(wordRepository)).get(WordViewModel::class.java)

        setContent {
            DictionaryTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "dictionary") {
                        composable("dictionary") {
                            DictionaryScreen(navController, wordViewModel)
                        }
                        composable("add_edit_word/{wordId}") { backStackEntry ->
                            val wordId = backStackEntry.arguments?.getString("wordId")?.toIntOrNull()
                            val word = wordId?.let { wordViewModel.getWordById(it) }
                            AddWordScreen(navController, wordViewModel, word)
                        }
                        composable("settings") {
                            SettingsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val wordRepository = WordRepository(LocalContext.current)
    val wordViewModel = WordViewModel(wordRepository)
    val navController = rememberNavController()

    // This allows previewing the main UI structure without the need to run MainActivity
    DictionaryTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            NavHost(navController = navController, startDestination = "dictionary") {
                composable("dictionary") {
                    DictionaryScreen(navController, wordViewModel)
                }
                composable("add_edit_word/{wordId}") { backStackEntry ->
                    val wordId = backStackEntry.arguments?.getString("wordId")?.toIntOrNull()
                    val word = wordId?.let { wordViewModel.getWordById(it) }
                    AddWordScreen(navController, wordViewModel, word)
                }
                composable("settings") {
                    SettingsScreen(navController = navController)
                }
            }
        }
    }
}
