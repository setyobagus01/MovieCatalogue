package com.example.moviecatalogue.ui.detail

import androidx.lifecycle.*
import com.example.moviecatalogue.core.domain.usecase.CatalogueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val catalogueUseCase: CatalogueUseCase
) : ViewModel() {
    private var _id = MutableLiveData<Int>()
    private val id: LiveData<Int> get() = _id


    fun setId(catalogueId: Int) {
        _id.value = catalogueId
    }

    var getMovie = Transformations.switchMap(id) { movieId ->
        catalogueUseCase.getDetailMovie(movieId).asLiveData()
    }

    var getTvShows = Transformations.switchMap(id) { tvShowId ->
        catalogueUseCase.getDetailTvShow(tvShowId).asLiveData()
    }

    fun setFavoriteMovie() {
        val movieResource = getMovie.value?.data
        if (movieResource != null) {
            val newState = !movieResource.isFavorite
            catalogueUseCase.setFavoriteFromMovie(movieResource, newState)
        }

    }

    fun setFavoriteTvShow() {
        val tvShowResource = getTvShows.value?.data
        if (tvShowResource != null) {
            val newState = !tvShowResource.isFavorite
            catalogueUseCase.setFavoriteFromTvShow(tvShowResource, newState)
        }

    }

}