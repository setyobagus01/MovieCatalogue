package com.example.moviecatalogue.core.data.source.local

import androidx.paging.DataSource
import com.example.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.example.moviecatalogue.core.data.source.local.room.CatalogueDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val catalogueDao: CatalogueDao) {
    fun getMovies(): DataSource.Factory<Int, MovieEntity> = catalogueDao.getMovies()

    fun getMovieById(movieId: Int): Flow<MovieEntity> =
        catalogueDao.getMovieById(movieId)

    fun getFavoriteFromMovies(): DataSource.Factory<Int, MovieEntity> =
        catalogueDao.getFavoriteFromMovies()

    suspend fun setFavoriteFromMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        catalogueDao.updateMovieById(movie)
    }

    suspend fun insertMovies(movies: List<MovieEntity>) = catalogueDao.insertMovies(movies)

    suspend fun updateMovieById(movie: MovieEntity) = catalogueDao.updateMovieById(movie)

    fun getTvShows(): DataSource.Factory<Int, TvShowEntity> = catalogueDao.getTvShows()

    fun getTvShowById(tvShowId: Int): Flow<TvShowEntity> =
        catalogueDao.getTvShowId(tvShowId)

    fun getFavoriteFromTvShows(): DataSource.Factory<Int, TvShowEntity> =
        catalogueDao.getFavoriteFromTvShow()

    suspend fun setFavoriteFromTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.isFavorite = newState
        catalogueDao.updateTvShowById(tvShow)
    }

    suspend fun insertTvShows(tvShows: List<TvShowEntity>) = catalogueDao.insertTvShows(tvShows)

    suspend fun updateTvShowById(tvShow: TvShowEntity) = catalogueDao.updateTvShowById(tvShow)
}