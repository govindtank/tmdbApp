package com.govindtank.tmdbapp.data.remote

import com.govindtank.tmdbapp.app.Constants.INCLUDE_ADULT
import com.govindtank.tmdbapp.app.Constants.LANGUAGE
import com.govindtank.tmdbapp.data.model.Movie
import com.govindtank.tmdbapp.data.model.TMDBResponse
import com.govindtank.tmdbapp.secrets.Secrets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDataSource @Inject constructor(private val tmdbApi: TMDBApi) {

    suspend fun getMoviesForType(typePath: String, page: Int) = tmdbApi.getMoviesForType(typePath, page = page)

    suspend fun getMovie(movieId: Long): Movie =
        tmdbApi.getMovie(movieId, Secrets.TMDB_API_KEY, LANGUAGE)

    suspend fun discoverMovies(position: Int): TMDBResponse =
        tmdbApi.discoverMovies(page = position)

    suspend fun searchMovies(query: String, position: Int): TMDBResponse =
        tmdbApi.searchMovies(Secrets.TMDB_API_KEY, LANGUAGE, query, position, INCLUDE_ADULT)

}