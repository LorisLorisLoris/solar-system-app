package com.example.kotlinplanetsapi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.kotlinplanetsapi.viewmodel.MainViewModel
import java.util.Locale

@Composable
fun BodyDetailsScreen(
    planetId: String,
    viewModel: MainViewModel = viewModel(),
    navHostController: NavHostController
) {
    // Récupére le corps astral correspondant à l'ID
    val foundBody = viewModel.bodiesList.find { it.id == planetId }

    if (foundBody != null) {
        // Affiche les détails
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Obtient l'emoji correspondant au type de la corps
            val planetEmoji = getPlanetEmoji(foundBody.bodyType)

            // Affiche l'emoji et le nom de la planète
            Text(text = "$planetEmoji", fontSize = 54.sp, lineHeight = 54.sp, textAlign = TextAlign.Center, style = MaterialTheme.typography.titleMedium)
            Text(text = foundBody.name, fontSize = 54.sp, lineHeight = 54.sp, textAlign = TextAlign.Center, style = MaterialTheme.typography.titleMedium)

            // Affiche seulement si le corps est en orbite autour d'une autre planète
            if (!foundBody.aroundPlanet?.planet.isNullOrBlank()){
                Text(text = "En orbite autour de ${
                    foundBody.aroundPlanet?.planet?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }}", fontSize = 18.sp
                )
            }


            Text(text = "Type : ${foundBody.bodyType}", fontSize = 18.sp)
            Text(text = "Demi-grand axe : ${foundBody.semimajorAxis} UA", fontSize = 18.sp)
            Text(text = "Gravité : ${foundBody.gravity ?: "Non renseigné"} m/s²", fontSize = 18.sp)
            Text(text = "Densité : ${foundBody.density ?: "Non renseignée"} kg/m³", fontSize = 18.sp)

            // Affiche dimension, decouvert par et la date selon condition
            if (
                !foundBody.dimension.isNullOrBlank() &&
                !foundBody.discoveredBy.isNullOrBlank() &&
                !foundBody.discoveryDate.isNullOrBlank() ){
                Text(text = "Dimension : ${foundBody.dimension}", fontSize = 18.sp)
                Text(text = "Découvert par ${foundBody.discoveredBy}", fontSize = 18.sp)
                Text(text = "le ${foundBody.discoveryDate}", fontSize = 18.sp)
            }

            // Bouton de retour à la liste
            IconButton(
                onClick = {
                    navHostController.popBackStack()
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    } else {
        // Affiche un message si la planète n'est pas trouvée
        Text(text = "Planète non trouvée", fontSize = 20.sp)
    }
}

@Composable
fun getPlanetEmoji(bodyType: String): String {
    // Renvoit l'emoji correspondant au type de planète
    return when (bodyType.toLowerCase()) {
        "moon" -> "🌖"
        "asteroid" -> "🪨"
        "comet" -> "☄️"
        "planet" -> "🌐"
        "star" -> "🌞"
        else -> ""
    }
}
