package com.example.moviebooking.networking.model

data class Buzz(
    val status: String = "",
    val totalResults: Int = 0,
    val articles: List<Article>
)
