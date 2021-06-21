package com.example.moviecatalogue.ui.pages.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.domain.usecase.CatalogueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    catalogueUseCase: CatalogueUseCase,
) :
    ViewModel() {

    val tvShows = catalogueUseCase.getTvShows().asLiveData()
}