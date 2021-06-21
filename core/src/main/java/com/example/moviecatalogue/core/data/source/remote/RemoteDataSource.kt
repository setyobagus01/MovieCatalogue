package com.example.moviecatalogue.core.data.source.remote

import com.example.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.example.moviecatalogue.core.data.source.remote.network.ApiService
import com.example.moviecatalogue.core.data.source.remote.response.CatalogueDetailResponse
import com.example.moviecatalogue.core.data.source.remote.response.CatalogueResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val service: ApiService) {

    suspend fun loadMovies(): Flow<ApiResponse<List<CatalogueResponse>>> {
        return flow {
            try {
                val response = service.getMovies()
                val catalogue = response.results

                if (catalogue.isNotEmpty()) {
                    emit(ApiResponse.Success(catalogue))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun loadDetailMovie(id: Int): Flow<ApiResponse<CatalogueDetailResponse>> {
        return flow {
            try {
                val response = service.getDetailMovie(id)
                emit(ApiResponse.Success(response))

            } catch (e: IOException) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    }

    suspend fun loadTvShows(): Flow<ApiResponse<List<CatalogueResponse>>> {
        return flow {
            try {
                val response = service.getTvShows()
                val catalogue = response.results

                if (catalogue.isNotEmpty()) {
                    emit(ApiResponse.Success(catalogue))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun loadDetailTvShow(id: Int): Flow<ApiResponse<CatalogueDetailResponse>> {
        return flow {
            try {
                val response = service.getDetailTvShow(id)
                emit(ApiResponse.Success(response))

            } catch (e: IOException) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}


