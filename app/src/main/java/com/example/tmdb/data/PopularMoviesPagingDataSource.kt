package com.example.tmdb.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.utils.Result
import com.example.tmdb.model.Movie
import com.example.tmdb.remote.MoviesApi
import com.example.tmdb.utils.apiValidator

/**
 * Created by Shaheer cs on 22/03/2022.
 */
private const val STARTING_PAGE_INDEX = 1
private const val ONE = 1

class PopularMoviesPagingDataSource(private val moviesApi: MoviesApi) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        return when (val response = apiValidator { moviesApi.getPopularMoviesList(pageNumber) }) {
            is Result.Success -> {
                LoadResult.Page(
                    data = response.data.movies,
                    prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber.minus(ONE),
                    nextKey = if (response.data.movies.isEmpty()) null else pageNumber.plus(ONE)
                )
            }
            is Result.Error -> {
                LoadResult.Error(Exception(response.message))
            }
            else -> {
                LoadResult.Error(Exception(MoviesApi.UNKNOWN_ERROR))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(ONE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(ONE)
        }
    }
}