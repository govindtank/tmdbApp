package com.govindtank.tmdbapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.govindtank.tmdbapp.data.model.Movie
import com.govindtank.tmdbapp.data.remote.MoviesPagingSource
import com.govindtank.tmdbapp.data.remote.TMDBApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepo @Inject constructor(private val api: TMDBApi) {
//    fun getMoviesResult(): LiveData<PagingData<Movie>> =
//        Pager(
//            config = PagingConfig(
//                pageSize = 20,
//                maxSize = 100,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { MoviesPagingSource(api) }
//        ).liveData
}