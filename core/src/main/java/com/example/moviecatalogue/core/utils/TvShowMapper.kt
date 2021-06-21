package com.example.moviecatalogue.core.utils

import com.example.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.example.moviecatalogue.core.data.source.remote.response.CatalogueDetailResponse
import com.example.moviecatalogue.core.data.source.remote.response.CatalogueResponse
import com.example.moviecatalogue.core.domain.model.Catalogue
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowMapper @Inject constructor() {

    fun mapToEntity(domain: Catalogue): TvShowEntity {
        return TvShowEntity(
            id = domain.id,
            name = domain.name,
            imgPoster = domain.imgPoster,
            releaseDate = domain.releaseDate,
            overview = domain.overview,
            genres = domain.genres,
            userScore = domain.userScore,
            tagline = domain.tagline,
            isFavorite = domain.isFavorite,
            duration = domain.duration,
            popularity = domain.popularity
        )
    }

    fun mapFromEntity(entity: TvShowEntity): Catalogue {
        return Catalogue(
            id = entity.id,
            name = entity.name,
            imgPoster = entity.imgPoster,
            releaseDate = entity.releaseDate,
            overview = entity.overview,
            isFavorite = entity.isFavorite,
            popularity = entity.popularity
        )
    }

    fun mapFromDetailEntity(entity: TvShowEntity): Catalogue {
        return Catalogue(
            id = entity.id,
            name = entity.name,
            imgPoster = entity.imgPoster,
            releaseDate = entity.releaseDate,
            overview = entity.overview,
            genres = entity.genres,
            userScore = entity.userScore,
            tagline = entity.tagline,
            isFavorite = entity.isFavorite,
            duration = entity.duration,
            popularity = entity.popularity
        )
    }

    private fun mapFromNetwork(network: CatalogueResponse): TvShowEntity {
        return TvShowEntity(
            id = network.id,
            name = network.name,
            imgPoster = "https://image.tmdb.org/t/p/w500${network.posterPath}",
            releaseDate = network.firstAirDate,
            overview = network.overview,
            popularity = network.popularity
        )
    }

    fun mapFromDetailNetwork(network: CatalogueDetailResponse): TvShowEntity {
        return TvShowEntity(
            id = network.id,
            name = network.name,
            imgPoster = "https://image.tmdb.org/t/p/w500${network.posterPath}",
            releaseDate = network.firstAirDate,
            overview = network.overview,
            duration = network.episodeRunTime[0].toString(),
            genres = network.genres.joinToString { it.name },
            userScore = network.voteAverage,
            tagline = network.tagline,
            popularity = network.popularity
        )
    }

    fun mapFromNetworkList(entities: List<CatalogueResponse>): List<TvShowEntity> {
        return entities.map { mapFromNetwork(it) }
    }



}