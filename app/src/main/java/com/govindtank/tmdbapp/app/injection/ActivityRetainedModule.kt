package com.govindtank.tmdbapp.app.injection

import com.govindtank.tmdbapp.domain.MoviesRepository
import com.govindtank.tmdbapp.domain.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

  @Binds
  abstract fun bindMovieRepository(
    moviesRepositoryImpl: MoviesRepositoryImpl
  ): MoviesRepository
}