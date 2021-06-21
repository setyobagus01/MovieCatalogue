package com.example.moviecatalogue.core.di

import com.example.moviecatalogue.core.data.CatalogueRepository
import com.example.moviecatalogue.core.domain.repository.ICatalogueRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(catalogueRepository: CatalogueRepository): ICatalogueRepository

}