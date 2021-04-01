package com.govindtank.tmdbapp.app.injection

import android.content.Context
import androidx.room.Room
import com.govindtank.tmdbapp.app.Constants.DATABASE_NAME
import com.govindtank.tmdbapp.app.Constants.TMDB_URL
import com.govindtank.tmdbapp.data.local.AppDatabase
import com.govindtank.tmdbapp.data.remote.TMDBApi
import com.govindtank.tmdbapp.domain.MoviesRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(TMDB_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): TMDBApi = retrofit.create(TMDBApi::class.java)

    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase) = database.movieDao()

}