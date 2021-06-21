package com.example.moviecatalogue.core.domain.usecase

import androidx.paging.PagedList
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Catalogue
import kotlinx.coroutines.flow.Flow

interface CatalogueUseCase {
    fun getMovies(): Flow<Resource<PagedList<Catalogue>>>

    fun getTvShows(): Flow<Resource<PagedList<Catalogue>>>

    fun getDetailMovie(movieId: Int): Flow<Resource<Catalogue>>

    fun getDetailTvShow(tvShowId: Int): Flow<Resource<Catalogue>>

    fun getFavoriteFromMovie(): Flow<PagedList<Catalogue>>

    fun getFavoriteFromTvShow(): Flow<PagedList<Catalogue>>

    fun setFavoriteFromMovie(catalogue: Catalogue, state: Boolean)

    fun setFavoriteFromTvShow(catalogue: Catalogue, state: Boolean)
}