package com.example.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CatalogueGenresResponse(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)
