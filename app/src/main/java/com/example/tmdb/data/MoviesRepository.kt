package com.example.tmdb.data

import androidx.paging.PagingData
import com.example.tmdb.model.Movie
import com.example.tmdb.model.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow
import com.example.tmdb.utils.Result

/**
 * Created by Shaheer cs on 22/03/2022.
 */
interface MoviesRepository {

    fun getPopularMovieDataStream(): Flow<PagingData<Movie>>

     fun getSelectedMovieDetails(movieId: Int): Flow<Result<MovieDetailsResponse?>>

}