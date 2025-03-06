package com.example.psymarzecv2.ui.motyw.psy

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.psymarzecv2.dane.modele.Dog

@Composable
fun DogsScreen(
    modifier: Modifier = Modifier,
    viewModel: DogsViewModel = hiltViewModel(),
    navigationController: NavController,
) {
    Log.d("DogsScreen", "DogsScreen composable started")
    val items by viewModel.uiState.collectAsStateWithLifecycle()

    when (items) {
        is UiState.Success -> {
            Log.d("DogsScreen", "Showing success state with ${(items as UiState.Success).data.size} dogs")
            DogListContent(
                items = (items as UiState.Success).data,
                onAdd = viewModel::addDog,
                onDeleteClick = viewModel::removeDog,
                onFavoriteClick = viewModel::triggerFav,
                onDogClick = { dogId -> navigationController.navigate("details/$dogId") },
                onSetClick = { navigationController.navigate("settings") },
                onProClick = { navigationController.navigate("profile") },
                modifier = modifier,
            )
        }
        is UiState.Loading -> {
            Log.d("DogsScreen", "Showing loading state")
            Text(text = "Ładowanie...", style = MaterialTheme.typography.bodyLarge)
        }
        is UiState.Error -> {
            Log.e("DogsScreen", "Showing error state", (items as UiState.Error).throwable)
            Text(
                text = "Błąd: ${(items as UiState.Error).throwable.message}",
                color = MaterialTheme.colorScheme.error
            )
        }
        else -> {

            Log.e("DogsScreen", "Unexpected state: $items")
            Text(text = "Nieoczekiwany błąd", color = MaterialTheme.colorScheme.error)
        }
    }
}
@Composable
fun DogListContent(
    items: List<Dog>,
    onFavoriteClick: (id: Int) -> Unit,
    onAdd: (name: String) -> Unit,
    onDeleteClick: (id: Int) -> Unit,
    onDogClick: (dogId: Int) -> Unit,
    onSetClick: () -> Unit,
    onProClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var searchQuery by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }
        var isSearching by remember { mutableStateOf(false) }

        val searchResult = remember(items, searchQuery, isSearching) {
            val filtered = if (isSearching && searchQuery.isNotEmpty()) {
                items.filter { it.name.contains(searchQuery, ignoreCase = true) }
            } else {
                items
            }
            filtered.sortedByDescending { it.isFav } // 🌟 Ulubione psy są zawsze na górze
        }

        val totalDogs = items.size
        val favoriteDogs = items.count { it.isFav }

        // pasek
        CustomTopBar(
            title = "Lista Psów",
            onSettingsClick = onSetClick,
            onProfileClick = onProClick
        )

        // szukanie
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    isSearching = true
                },
                placeholder = { Text("Wyszukaj lub dodaj Psa \uD83E\uDDAE") },
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    if (searchQuery.isBlank()) {
                        errorMessage = "Wprowadź imię psa!"
                    } else if (items.any { it.name.equals(searchQuery, ignoreCase = true) }) {
                        errorMessage = "Pies już jest na liście!"
                    } else {
                        onAdd(searchQuery)
                        searchQuery = ""
                        errorMessage = ""
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Dodaj psa",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }

        Text(
            text = "\uD83E\uDDAE: $totalDogs    ❤\uFE0F: $favoriteDogs",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 5.dp)
        )



        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(searchResult.size) { index ->
                val dog = searchResult[index]
                DogItem(
                    dog = dog,
                    onFavoriteClick = { onFavoriteClick(dog.id) },
                    onDeleteClick = { onDeleteClick(dog.id) },
                    onDogClick = { onDogClick(dog.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Ustawienia",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profil",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun DogItem(
    dog: Dog,
    onFavoriteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDogClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onDogClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = dog.imageUrl,
                contentDescription = "Zdjęcie psa",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = dog.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = dog.breed,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (dog.isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Ulubione",
                    tint = if (dog.isFav) Color.Red else Color.Gray
                )
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Usuń",
                    tint = Color.Red
                )
            }
        }
    }
}
