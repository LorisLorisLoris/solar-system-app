package com.example.kotlinplanetsapi.data

data class Body(
    val name: String,
    val semimajorAxis: Double,
    val bodyType: String,
    val id: String,
    val gravity: Number?,
    val density: Number?,
    val dimension: String?,
    val aroundPlanet: AroundPlanet?,
    val discoveredBy: String?,
    val discoveryDate: String?
)

data class AroundPlanet(
    val planet: String?
)
