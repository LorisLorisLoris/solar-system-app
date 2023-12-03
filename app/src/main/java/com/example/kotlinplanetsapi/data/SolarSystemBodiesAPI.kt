package com.example.kotlinplanetsapi.data

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

suspend fun main() {
    val solarSystemBodies = SolarSystemBodiesAPI()
    val planets = solarSystemBodies.fetchSolarSystemBodies()
    planets.forEach { planet ->
        println(planet.name)

    }
}

class SolarSystemBodiesAPI {
    private val apiUrl = "https://api.le-systeme-solaire.net/rest/bodies"
    private val client = OkHttpClient()

    // Fonction pour obtenir la liste des corps du système solaire
    suspend fun fetchSolarSystemBodies(): List<Body> {
        val bodies = mutableListOf<Body>()

        try {
            // Crée une requête pour l'URL de l'API
            val request = Request.Builder().url(apiUrl).build()

            // Exécute la requête et obtenir la réponse
            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }

            // Vérifie si la réponse est réussie
            if (!response.isSuccessful) {
                throw Exception("Code inattendu : $response")
            }

            // Pour convertir la réponse en objet SolarSystemApiResponse
            val gson = Gson()
            val responseBody = response.body?.string()
            val responseObj = gson.fromJson(responseBody, SolarSystemApiResponse::class.java)

            // Ajoute les corps du système solaire à la liste et trie par distance
            bodies.addAll(responseObj?.bodies?.sortedBy { it.semimajorAxis } ?: emptyList())

        } catch (e: Exception) {
            // Affiche les détails de l'erreur en cas d'échec
            e.printStackTrace()
        }

        // Renvoi la liste des corps du système solaire
        return bodies
    }
}

data class SolarSystemApiResponse(val bodies: List<Body>)


