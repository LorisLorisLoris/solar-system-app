package com.example.kotlinplanetsapi.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.kotlinplanetsapi.data.Body
import com.example.kotlinplanetsapi.router.Routes
import com.example.kotlinplanetsapi.viewmodel.MainViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController
) {
    // Chargement initial des planètes
    LaunchedEffect(key1 = true) {
        viewModel.loadBodies()
    }

    Column(modifier = modifier) {
        // Barre de recherche
        SearchBar(
            text = viewModel.searchText,
            onValueChange = { viewModel.uploadSearchText(it) },
            onClearClick = { viewModel.clearSearchText() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )

        // Boutons de filtre
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterButton("🌖", onClick = { viewModel.toggleFilter("moon") }, modifier = Modifier.padding(4.dp))
            FilterButton("🪨", onClick = { viewModel.toggleFilter("asteroid") }, modifier = Modifier.padding(4.dp))
            FilterButton("☄️", onClick = { viewModel.toggleFilter("comet") }, modifier = Modifier.padding(4.dp))
            FilterButton("🌍", onClick = { viewModel.toggleFilter("planet") }, modifier = Modifier.padding(4.dp))
            FilterButton("🌞", onClick = { viewModel.toggleFilter("star") }, modifier = Modifier.padding(4.dp))
        }

        // Affiche l'indicateur de chargement si la liste est en cours de chargement
        if (viewModel.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
        }

        // Liste des planètes
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(viewModel.filteredPlanetList) { planet ->
                PlanetItem(navController, planet)
            }
        }
    }
}

@Composable
fun FilterButton(emoji: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    // Bouton de filtre
    IconButton(
        onClick = onClick,
        modifier = modifier
            .width(65.dp)
            .height(30.dp)
    ) {
        Text(text = emoji, fontSize = 24.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetItem(navController: NavHostController, body: Body) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                // Utilisez la nouvelle route avec l'ID du corps astral
                navController.navigate("${Routes.BodyDetailsScreen.route}/${body.id}")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = body.name,
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    onClearClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            label = { Text("Rechercher") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        // Icône de croix pour effacer le texte
        if (text.isNotEmpty()) {
            IconButton(onClick = onClearClick) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
            }
        }
    }
}