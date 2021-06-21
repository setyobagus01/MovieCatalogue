package com.example.moviecatalogue.ui.pages.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.domain.usecase.CatalogueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    catalogueUseCase: CatalogueUseCase
) :
    ViewModel() {

    val movies = catalogueUseCase.getMovies().asLiveData()
}