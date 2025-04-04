package com.example.dictionary.ui.add_edit_word

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dictionary.viewmodel.WordViewModel
import com.example.dictionary.viewmodel.WordViewModelFactory
import com.example.dictionary.model.Word

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordScreen(
                  navController: NavController,
                  wordId: Int? = null,
                  wordViewModel: WordViewModel = viewModel(factory = WordViewModelFactory(LocalContext.current.applicationContext as Application))
) {
    var englishWord by remember { mutableStateOf("") }
    var mongolianMeaning by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(wordId) {
        if (wordId != null && wordId != 0) {
            wordViewModel.getWordById(wordId)?.let { word ->
                englishWord = word.englishWord
                mongolianMeaning = word.mongolianMeaning
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (wordId == null || wordId == 0) "Үг нэмэх" else "Үг засах",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = englishWord,
            onValueChange = { englishWord = it },
            label = { Text("Англи үг") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = mongolianMeaning,
            onValueChange = { mongolianMeaning = it },
            label = { Text("Монгол утга") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (englishWord.isNotBlank() && mongolianMeaning.isNotBlank()) {
                    val word = Word(
                        id = wordId ?: 0,
                        englishWord = englishWord,
                        mongolianMeaning = mongolianMeaning
                    )
                    if (wordId == null || wordId == 0) {
                        wordViewModel.insert(word)
                        Toast.makeText(context, "Үг нэмэгдлээ", Toast.LENGTH_SHORT).show()
                    } else {
                        wordViewModel.update(word)
                        Toast.makeText(context, "Үг шинэчлэгдлээ", Toast.LENGTH_SHORT).show()
                    }
                    navController.navigateUp()
                } else {
                    Toast.makeText(context, "Бүх талбарыг бөглөнө үү", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (wordId == null || wordId == 0) "Үг нэмэх" else "Үг засах")
        }
    }
}