package com.example.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab2.ui.theme.Lab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab2Theme {
                ArtGalleryApp()
            }
        }
    }
}

@Composable
fun ArtGalleryApp() {
    var currentStep by remember { mutableStateOf(1) }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentStep) {
                1 -> {
                    ArtContent(
                        imageResId = R.drawable.vancouver_british_columbia,
                        titleResId = R.string.VancouverTitle,
                        descriptionResId = R.string.VancouverDescription,
                        onNextClick = { currentStep = 2 },
                        onPreviousClick = { currentStep = 3 }
                    )
                }
                2 -> {
                    ArtContent(
                        imageResId = R.drawable.pexels_photo_1563256,
                        titleResId = R.string.PexelTitle,
                        descriptionResId = R.string.PexelDescription,
                        onNextClick = { currentStep = 3 },
                        onPreviousClick = { currentStep = 1 }
                    )
                }
                3 -> {
                    ArtContent(
                        imageResId = R.drawable.city_landscape_at_night,
                        titleResId = R.string.CityTitle,
                        descriptionResId = R.string.CityDescription,
                        onNextClick = { currentStep = 1 },
                        onPreviousClick = { currentStep = 2 }
                    )
                }
            }
        }
    }

@Composable
fun ArtContent(
    imageResId: Int,
    titleResId: Int,
    descriptionResId: Int,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .shadow(8.dp ,RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(imageResId),
                contentDescription = stringResource(titleResId),
                modifier = Modifier.size(300.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .width(280.dp)
                .background(Color(0xFFEEEEEE))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = stringResource(titleResId),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(descriptionResId),
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onPreviousClick) {
                Text("Previous")
            }
            Button(onClick = onNextClick) {
                Text("Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArtGalleryApp() {
    Lab2Theme  {
        ArtGalleryApp()
    }
}
