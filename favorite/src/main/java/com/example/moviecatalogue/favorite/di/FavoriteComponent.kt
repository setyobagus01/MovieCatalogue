package com.example.moviecatalogue.favorite.di

import android.content.Context
import com.example.moviecatalogue.di.FavoriteModuleDependencies
import com.example.moviecatalogue.favorite.movies.MovieFavoriteFragment
import com.example.moviecatalogue.favorite.tvshows.TvShowFavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(movieFavoriteFragment: MovieFavoriteFragment)
    fun inject(tvShowFavoriteFragment: TvShowFavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}