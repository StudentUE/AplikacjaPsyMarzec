package com.example.psymarzecv2.ui.motyw.szczegoly

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.psymarzecv2.ui.motyw.TopBar

@Composable
fun DogDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: DogDetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val dog by viewModel.dog.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopBar(
            title = dog?.name ?: "Dog Details",
            canNavigateBack = true,
            onNavigateBack = onNavigateBack
        )

        dog?.let { dogData ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                AsyncImage(
                    model = dogData.imageUrl,
                    contentDescription = "Zdjęcie psa",
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFFF9F9F9), Color(0xFFE0E0E0))
                            )
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dogData.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Breed: ${dogData.breed}",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = if (dogData.isFav) "❤️ Favorite" else "🤍 Not favorite",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
