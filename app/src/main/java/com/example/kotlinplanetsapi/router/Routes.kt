package com.example.kotlinplanetsapi.router


sealed class Routes(val route: String) {
    object SearchScreen : Routes("searchScreen")
    object BodyDetailsScreen : Routes("BodyDetailsScreen")
}