package com.govindtank.tmdbapp.data.remote

import androidx.paging.PagingSource
import com.govindtank.tmdbapp.data.model.Movie
import com.govindtank.tmdbapp.secrets.Secrets
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TMDB_STARTING_PAGE_INDEX = 1

@Singleton
class MoviesPagingSource @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val typePath: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = networkDataSource.getMoviesForType(typePath, position)
            val movies = response.results
            LoadResult.Page(
                data = movies,
                prevKey = if (position == TMDB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}