package com.example.dictionary.ui.main

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dictionary.viewmodel.WordViewModel
import com.example.dictionary.viewmodel.WordViewModelFactory
import com.example.dictionary.ui.settings.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DictionaryScreen(
    navController: NavController,
    wordViewModel: WordViewModel = viewModel(factory = WordViewModelFactory(LocalContext.current.applicationContext as Application)),
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val words by wordViewModel.allWords.collectAsState(initial = emptyList())
    val displayMode by settingsViewModel.setting.collectAsState()
    var currentIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current
    var toastMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            toastMessage = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Үгийн жагсаалт") },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Үгийн жагсаалт", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            val word = words.getOrNull(currentIndex)
            if (word != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = when (displayMode) {
                            "english" -> word.englishWord
                            "mongolian" -> ""
                            else -> word.englishWord
                        },
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Англи үг") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .combinedClickable(
                                onClick = { },
                                onLongClick = { navController.navigate("add_edit_word/${word.id}") }
                            )
                    )

                    OutlinedTextField(
                        value = when (displayMode) {
                            "english" -> ""
                            "mongolian" -> word.mongolianMeaning
                            else -> word.mongolianMeaning
                        },
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Монгол утга") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = { },
                                onLongClick = { navController.navigate("add_edit_word/${word.id}") }
                            )
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Англи үг") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Монгол утга") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { if (currentIndex > 0) currentIndex-- },
                    enabled = words.isNotEmpty()
                ) {
                    Text("Өмнөх")
                }
                Button(
                    onClick = { if (currentIndex < words.size - 1) currentIndex++ },
                    enabled = words.isNotEmpty()
                ) {
                    Text("Дараах")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("add_edit_word/0") },
                enabled = true
            ) {
                Text("Үг нэмэх")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    word?.let {
                        wordViewModel.delete(it)
                        toastMessage = "Үг устгагдлаа"
                        if (currentIndex > 0) currentIndex--
                    }
                },
                enabled = words.isNotEmpty()
            ) {
                Text("Үг устгах")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { word?.let { navController.navigate("add_edit_word/${word.id}") } },
                enabled = words.isNotEmpty()
            ) {
                Text("Үг засах")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDictionaryScreen() {
    val context = LocalContext.current
    val wordViewModel: WordViewModel = viewModel(factory = WordViewModelFactory(context.applicationContext as Application))
    val settingsViewModel: SettingsViewModel = viewModel()
    DictionaryScreen(
        navController = rememberNavController(),
        wordViewModel = wordViewModel,
        settingsViewModel = settingsViewModel
    )
}