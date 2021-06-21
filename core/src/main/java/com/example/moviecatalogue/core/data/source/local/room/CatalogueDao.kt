package com.example.moviecatalogue.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.example.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogueDao {
    @Query("SELECT * FROM movieEntities")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieEntities WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity>

    @Query("SELECT * FROM movieEntities WHERE isFavorite = 1")
    fun getFavoriteFromMovies(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    suspend fun updateMovieById(movie: MovieEntity)

    @Query("SELECT * FROM tvShowEntities")
    fun getTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tvShowEntities WHERE tvShowId = :tvShowId")
    fun getTvShowId(tvShowId: Int): Flow<TvShowEntity>

    @Query("SELECT * FROM tvShowEntities WHERE isFavorite = 1")
    fun getFavoriteFromTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(movies: List<TvShowEntity>)

    @Update
    suspend fun updateTvShowById(tvShow: TvShowEntity)
}