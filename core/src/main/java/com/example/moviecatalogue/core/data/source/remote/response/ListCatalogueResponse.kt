package com.example.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListCatalogueResponse(
    @field:SerializedName("results")
    var results: List<CatalogueResponse>,
)


