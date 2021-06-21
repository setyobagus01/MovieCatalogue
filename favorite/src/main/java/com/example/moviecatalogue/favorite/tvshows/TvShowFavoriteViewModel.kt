package com.example.moviecatalogue.favorite.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.domain.model.Catalogue
import com.example.moviecatalogue.core.domain.usecase.CatalogueUseCase


class TvShowFavoriteViewModel(
    private val catalogueUseCase: CatalogueUseCase
) :
    ViewModel() {

    val favoriteTvShows = catalogueUseCase.getFavoriteFromTvShow().asLiveData()

    fun setFavoriteTvShow(catalogue: Catalogue) {
        val newState = !catalogue.isFavorite
        catalogueUseCase.setFavoriteFromTvShow(catalogue, newState)
    }
}