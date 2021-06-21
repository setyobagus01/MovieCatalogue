package com.example.moviecatalogue.core.domain.usecase

import androidx.paging.PagedList
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Catalogue
import com.example.moviecatalogue.core.domain.repository.ICatalogueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogueInteractor @Inject constructor(private val catalogueRepository: ICatalogueRepository) :
    CatalogueUseCase {
    override fun getMovies(): Flow<Resource<PagedList<Catalogue>>> = catalogueRepository.getMovies()

    override fun getTvShows(): Flow<Resource<PagedList<Catalogue>>> =
        catalogueRepository.getTvShows()

    override fun getDetailMovie(movieId: Int): Flow<Resource<Catalogue>> =
        catalogueRepository.getDetailMovie(movieId)

    override fun getDetailTvShow(tvShowId: Int): Flow<Resource<Catalogue>> =
        catalogueRepository.getDetailTvShow(tvShowId)

    override fun getFavoriteFromMovie(): Flow<PagedList<Catalogue>> =
        catalogueRepository.getFavoriteFromMovie()

    override fun getFavoriteFromTvShow(): Flow<PagedList<Catalogue>> =
        catalogueRepository.getFavoriteFromTvShow()

    override fun setFavoriteFromMovie(catalogue: Catalogue, state: Boolean) =
        catalogueRepository.setFavoriteFromMovie(catalogue, state)

    override fun setFavoriteFromTvShow(catalogue: Catalogue, state: Boolean) =
        catalogueRepository.setFavoriteFromTvShow(catalogue, state)
}