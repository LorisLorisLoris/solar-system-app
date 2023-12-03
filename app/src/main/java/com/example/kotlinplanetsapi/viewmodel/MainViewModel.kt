package com.example.kotlinplanetsapi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinplanetsapi.data.Body
import com.example.kotlinplanetsapi.data.SolarSystemBodiesAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // Liste des planètes
    private var _bodiesList = mutableStateListOf<Body>()

    val bodiesList: List<Body> = _bodiesList

    var errorMessage by mutableStateOf("")

    // Texte de recherche
    var searchText by mutableStateOf("")
        private set

    // Liste des planètes filtrées par type de corps
    private var _filteredByBodyType = mutableStateListOf<Body>()

    val filteredByBodyType: List<Body> = _filteredByBodyType

    // État de chargement
    var isLoading by mutableStateOf(true)
        private set

    // Type de corps sélectionné
    var selectedBodyType by mutableStateOf("")
        private set

    // Liste des planètes filtrées
    val filteredPlanetList: List<Body>
        get() = _bodiesList.filter {
            it.name.contains(searchText, ignoreCase = true) &&
                    (selectedBodyType.isBlank() || it.bodyType.equals(selectedBodyType, ignoreCase = true))
        }

    // Fonction pour mettre à jour le texte de recherche
    fun uploadSearchText(newText: String) {
        searchText = newText
        applyFilters()
    }

    // Fonction pour effacer le texte de recherche
    fun clearSearchText() {
        searchText = ""
        applyFilters()
    }

    // Fonction pour basculer le filtre du type de corps
    fun toggleFilter(bodyType: String) {
        selectedBodyType = if (selectedBodyType == bodyType) "" else bodyType
        applyFilters()
    }

    // Fonction pour appliquer les filtres et mettre à jour la liste filtrée
    private fun applyFilters() {
        _filteredByBodyType.clear()
        _filteredByBodyType.addAll(filteredPlanetList)
    }

    // Fonction pour charger la liste des planètes à partir de l'API
    fun loadBodies() {
        viewModelScope.launch(Dispatchers.Default) {
            _bodiesList.clear()
            isLoading = true

            try {
                val apiReader = SolarSystemBodiesAPI()
                _bodiesList.addAll(apiReader.fetchSolarSystemBodies())

                isLoading = false
                applyFilters()

            } catch (e: Exception) {
                // Gestion des erreurs
                errorMessage = e.message ?: "An error occurred"
                println("Error loading planets: $errorMessage")
            }
        }
    }
}
