package com.example.moviecatalogue.di

import com.example.moviecatalogue.core.data.CatalogueRepository
import com.example.moviecatalogue.core.domain.usecase.CatalogueInteractor
import com.example.moviecatalogue.core.domain.usecase.CatalogueUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCatalogueUseCase(catalogueRepository: CatalogueRepository): CatalogueUseCase =
        CatalogueInteractor(catalogueRepository)
}