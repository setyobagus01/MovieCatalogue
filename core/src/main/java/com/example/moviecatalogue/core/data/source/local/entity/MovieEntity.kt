package com.example.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieEntities")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    @NonNull
    var id: Int,

    @ColumnInfo(name = "catalogueName")
    var name: String,

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String,

    @ColumnInfo(name = "genres")
    var genres: String? = null,

    @ColumnInfo(name = "userScore")
    var userScore: Double? = null,

    @ColumnInfo(name = "tagline")
    var tagline: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "duration")
    var duration: String? = null,

    @ColumnInfo(name = "imgPoster")
    var imgPoster: String,

    @ColumnInfo(name = "popularity")
    var popularity: Double,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)