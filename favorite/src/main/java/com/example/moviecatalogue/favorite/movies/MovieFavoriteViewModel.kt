package com.example.moviecatalogue.favorite.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.domain.model.Catalogue
import com.example.moviecatalogue.core.domain.usecase.CatalogueUseCase


class MovieFavoriteViewModel(
    private val catalogueUseCase: CatalogueUseCase
) :
    ViewModel() {

    val favoriteMovies = catalogueUseCase.getFavoriteFromMovie().asLiveData()


    fun setFavoriteMovie(catalogue: Catalogue) {
        val newState = !catalogue.isFavorite
        catalogueUseCase.setFavoriteFromMovie(catalogue, newState)
    }
}