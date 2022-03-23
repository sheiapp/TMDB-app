package com.example.tmdb.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Shaheer cs on 22/03/2022.
 */
class MoviesRepositoryImpl @Inject constructor(
    private val movieDetailsDataSource: MovieDetailsDataSource,
    private val popularMoviesPagingDataSource: PopularMoviesPagingDataSource
) : MoviesRepository {

    override fun getSearchResultStream(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = NETWORK_MAX_PAGE_SIZE
            ),
            pagingSourceFactory = { popularMoviesPagingDataSource }
        ).flow
    }

    override fun getSelectedMovieDetails(movieId: Int) =
        movieDetailsDataSource.getSelectedMovieDetails(movieId)

    companion object {
        const val NETWORK_PAGE_SIZE = 20
        const val NETWORK_MAX_PAGE_SIZE = 100
    }
}