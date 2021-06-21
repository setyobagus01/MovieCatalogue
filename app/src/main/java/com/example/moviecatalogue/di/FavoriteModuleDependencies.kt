package com.example.moviecatalogue.di

import com.example.moviecatalogue.core.domain.usecase.CatalogueUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun catalogueUseCase(): CatalogueUseCase
}