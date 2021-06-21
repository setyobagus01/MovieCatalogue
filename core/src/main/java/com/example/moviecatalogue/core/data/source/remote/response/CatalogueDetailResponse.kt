package com.example.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CatalogueDetailResponse(

    @field:SerializedName("title")
    var title: String,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("first_air_date")
    var firstAirDate: String,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("genres")
    var genres: List<CatalogueGenresResponse>,

    @field:SerializedName("id")
    var id: Int,

    @field:SerializedName("overview")
    var overview: String,

    @field:SerializedName("runtime")
    val runtime: Int,

    @field:SerializedName("poster_path")
    var posterPath: String,

    @field:SerializedName("release_date")
    var releaseDate: String,

    @field:SerializedName("vote_average")
    var voteAverage: Double,

    @field:SerializedName("tagline")
    var tagline: String,

    @field:SerializedName("episode_run_time")
    val episodeRunTime: List<Int>

)



