package com.example.moviecatalogue.core.data

import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviecatalogue.core.data.source.local.LocalDataSource
import com.example.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.example.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.example.moviecatalogue.core.data.source.remote.response.CatalogueDetailResponse
import com.example.moviecatalogue.core.data.source.remote.response.CatalogueResponse
import com.example.moviecatalogue.core.domain.model.Catalogue
import com.example.moviecatalogue.core.domain.repository.ICatalogueRepository
import com.example.moviecatalogue.core.utils.MovieMapper
import com.example.moviecatalogue.core.utils.TvShowMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogueRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val movieMapper: MovieMapper,
    private val tvShowMapper: TvShowMapper
) :
    ICatalogueRepository {

    override fun getMovies(): Flow<Resource<PagedList<Catalogue>>> {
        return object : NetworkBoundResource<PagedList<Catalogue>, List<CatalogueResponse>>() {
            override fun loadFromDB(): Flow<PagedList<Catalogue>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                val data = localDataSource.getMovies().map { movieMapper.mapFromEntity(it) }
                return LivePagedListBuilder(data, config).build().asFlow()
            }

            override fun shouldFetch(data: PagedList<Catalogue>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<CatalogueResponse>>> =
                remoteDataSource.loadMovies()

            override suspend fun saveCallResult(data: List<CatalogueResponse>) {
                val movies = movieMapper.mapFromNetworkList(data)
                localDataSource.insertMovies(movies)
            }

        }.asFlow()
    }


    override fun getTvShows(): Flow<Resource<PagedList<Catalogue>>> {
        return object : NetworkBoundResource<PagedList<Catalogue>, List<CatalogueResponse>>() {
            override fun loadFromDB(): Flow<PagedList<Catalogue>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setPageSize(4)
                    .build()
                val data = localDataSource.getTvShows().map { tvShowMapper.mapFromEntity(it) }
                return LivePagedListBuilder(data, config).build().asFlow()
            }

            override fun shouldFetch(data: PagedList<Catalogue>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<CatalogueResponse>>> =
                remoteDataSource.loadTvShows()

            override suspend fun saveCallResult(data: List<CatalogueResponse>) {
                val tvShows = tvShowMapper.mapFromNetworkList(data)
                localDataSource.insertTvShows(tvShows)
            }

        }.asFlow()

    }

    override fun getDetailMovie(movieId: Int): Flow<Resource<Catalogue>> {
        return object : NetworkBoundResource<Catalogue, CatalogueDetailResponse>() {
            override fun loadFromDB(): Flow<Catalogue> =
                localDataSource.getMovieById(movieId).map { movieMapper.mapFromDetailEntity(it) }

            override fun shouldFetch(data: Catalogue?): Boolean =
                data?.genres == null

            override suspend fun createCall(): Flow<ApiResponse<CatalogueDetailResponse>> =
                remoteDataSource.loadDetailMovie(movieId)

            override suspend fun saveCallResult(data: CatalogueDetailResponse) {
                val movie = movieMapper.mapFromDetailNetwork(data)
                localDataSource.updateMovieById(movie)
            }
        }.asFlow()
    }


    override fun getDetailTvShow(tvShowId: Int): Flow<Resource<Catalogue>> {
        return object : NetworkBoundResource<Catalogue, CatalogueDetailResponse>() {
            override fun loadFromDB(): Flow<Catalogue> =
                localDataSource.getTvShowById(tvShowId).map { tvShowMapper.mapFromDetailEntity(it) }

            override fun shouldFetch(data: Catalogue?): Boolean =
                data?.genres == null

            override suspend fun createCall(): Flow<ApiResponse<CatalogueDetailResponse>> =
                remoteDataSource.loadDetailTvShow(tvShowId)

            override suspend fun saveCallResult(data: CatalogueDetailResponse) {
                val tvShow = tvShowMapper.mapFromDetailNetwork(data)
                localDataSource.updateTvShowById(tvShow)
            }
        }.asFlow()
    }

    override fun getFavoriteFromMovie(): Flow<PagedList<Catalogue>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        val data =
            localDataSource.getFavoriteFromMovies().map { movieMapper.mapFromEntity(it) }
        return LivePagedListBuilder(data, config).build().asFlow()
    }

    override fun setFavoriteFromMovie(catalogue: Catalogue, state: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = movieMapper.mapToEntity(catalogue)
            localDataSource.setFavoriteFromMovie(data, state)
        }
    }


    override fun getFavoriteFromTvShow(): Flow<PagedList<Catalogue>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        val data =
            localDataSource.getFavoriteFromTvShows().map { tvShowMapper.mapFromEntity(it) }
        return LivePagedListBuilder(data, config).build().asFlow()
    }

    override fun setFavoriteFromTvShow(catalogue: Catalogue, state: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = tvShowMapper.mapToEntity(catalogue)
            localDataSource.setFavoriteFromTvShow(data, state)
        }
    }


}