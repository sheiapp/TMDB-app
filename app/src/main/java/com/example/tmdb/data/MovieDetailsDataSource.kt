package com.example.tmdb.data

import com.example.tmdb.utils.Result
import com.example.tmdb.model.MovieDetailsResponse
import com.example.tmdb.remote.MoviesApi
import com.example.tmdb.utils.apiValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Shaheer cs on 22/03/2022.
 */
class MovieDetailsDataSource @Inject constructor(private val moviesApi: MoviesApi) {
    fun getSelectedMovieDetails(movieId: Int): Flow<Result<MovieDetailsResponse?>> = flow {
        emit(Result.Loading)
        emit(apiValidator { moviesApi.getMovieDetails(movieId) })
    }.flowOn(Dispatchers.IO)
}