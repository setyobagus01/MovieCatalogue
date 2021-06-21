package com.example.moviecatalogue.core.domain.model

data class Catalogue(
    val id: Int,
    val name: String,
    val releaseDate: String,
    var genres: String? = null,
    var userScore: Double? = null,
    var tagline: String? = null,
    val overview: String,
    val duration: String? = null,
    val imgPoster: String,
    val popularity: Double,
    var isFavorite: Boolean = false
)