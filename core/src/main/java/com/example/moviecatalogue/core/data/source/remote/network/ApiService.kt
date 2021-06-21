package com.example.moviecatalogue.core.data.source.remote.network


import com.example.academy.core.BuildConfig
import com.example.moviecatalogue.core.data.source.remote.response.CatalogueDetailResponse
import com.example.moviecatalogue.core.data.source.remote.response.ListCatalogueResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Get Popular Movies
    @GET("movie/popular")
    suspend fun getMovies(@Query("api_key") apiKey: String = BuildConfig.API_KEY): ListCatalogueResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): CatalogueDetailResponse

    @GET("tv/popular")
    suspend fun getTvShows(@Query("api_key") apiKey: String = BuildConfig.API_KEY): ListCatalogueResponse

    @GET("tv/{tv_id}")
    suspend fun getDetailTvShow(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): CatalogueDetailResponse

}