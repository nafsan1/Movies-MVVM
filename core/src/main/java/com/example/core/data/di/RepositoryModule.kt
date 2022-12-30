package com.example.core.data.di

import com.example.core.data.MoviesRepository
import com.example.core.domain.repository.IMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(moviesRepository: MoviesRepository):IMoviesRepository
}