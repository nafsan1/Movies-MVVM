package com.example.core.di

import com.example.core.domain.usecase.MoviesInteractor
import com.example.core.domain.usecase.MoviesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideTourismUseCase(moviesInteractor: MoviesInteractor): MoviesUseCase

}