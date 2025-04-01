package com.example.dictionary.ui.AddWords

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dictionary.data.WordRepository
import com.example.dictionary.model.Word
import com.example.dictionary.viewmodel.WordViewModel

@Composable
fun AddWordScreen(navController: NavController, wordViewModel: WordViewModel, word: Word?) {
    var englishWord by remember { mutableStateOf(word?.englishWord ?: "") }
    var mongolianMeaning by remember { mutableStateOf(word?.mongolianMeaning ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = englishWord,
            onValueChange = { englishWord = it },
            label = { Text("Гадаад үг") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = mongolianMeaning,
            onValueChange = { mongolianMeaning = it },
            label = { Text("Монгол утга") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Save Button (Хадгалах)
            Button(onClick = {
                val newWord = Word(englishWord = englishWord, mongolianMeaning = mongolianMeaning)
                if (word != null) {
                    wordViewModel.updateWord(newWord)
                } else {
                    wordViewModel.addWord(newWord)
                }
                navController.popBackStack() // Go back to the previous screen
            }) {
                Text("Хадгалах")
            }

            // Cancel Button (Болих)
            Button(onClick = {
                navController.popBackStack() // Go back to the previous screen without saving
            }) {
                Text("Болих")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddWordScreen() {
    AddWordScreen(navController = rememberNavController(), wordViewModel = WordViewModel(
        WordRepository(LocalContext.current)
    ), word = null)
}
