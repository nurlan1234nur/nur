import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dictionary.data.WordRepository
import com.example.dictionary.viewmodel.WordViewModel

@Composable
fun DictionaryScreen(navController: NavController, wordViewModel: WordViewModel) {
    val words = wordViewModel.words.value
    var currentIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current
    var toastMessage by remember { mutableStateOf<String?>(null) }

    // To show Toast message on state change
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            toastMessage = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Үгийн жагсаалт", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Display the current word in two languages
        val word = words.getOrNull(currentIndex)
        word?.let {
            Text("${it.englishWord}: ${it.mongolianMeaning}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Previous Button
            Button(onClick = {
                if (currentIndex > 0) currentIndex--
            }) {
                Text("Өмнөх")
            }

            // Next Button
            Button(onClick = {
                if (currentIndex < words.size - 1) currentIndex++
            }) {
                Text("Дараах")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Word Button
        Button(onClick = {
            navController.navigate("add_edit_word/0") // "0" means add new word
        }) {
            Text("Үг нэмэх")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Delete Word Button
        Button(onClick = {
            if (word != null) {
                wordViewModel.deleteWord(word)
                toastMessage = "Үг устгагдлаа"
            }
        }) {
            Text("Үг устгах")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Update Word Button
        Button(onClick = {
            word?.let {
                navController.navigate("add_edit_word/${it.id}")
            }
        }) {
            Text("Үг шинэчлэх")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDictionaryScreen() {
    DictionaryScreen(navController = rememberNavController(), wordViewModel = WordViewModel(
        WordRepository(LocalContext.current)
    ))
}
