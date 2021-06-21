package com.example.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CatalogueResponse(
    @field:SerializedName("overview")
    var overview: String,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("title")
    var title: String,

    @field:SerializedName("media_type")
    val mediaType: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("poster_path")
    var posterPath: String,

    @field:SerializedName("release_date")
    var releaseDate: String,

    @field:SerializedName("first_air_date")
    var firstAirDate: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("id")
    var id: Int,
)
