package com.example.tmdb.remote

import com.example.tmdb.model.MovieDetailsResponse
import com.example.tmdb.model.PopularMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Shaheer cs on 22/03/2022.
 */
interface MoviesApi {
    @GET("popular?api_key=${API_KEY}&language=en-US")
    suspend fun getPopularMoviesList(@Query("page") pageNumber: Int): Response<PopularMovieResponse>

    @GET("{movie_id}?api_key=${API_KEY}&language=en-US")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Response<MovieDetailsResponse>

    companion object {
        const val API_KEY = "8311f58e6273bf80a2e6df467f8daaac"
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        const val UNKNOWN_ERROR = "An unknown error occurred"
        const val SERVER_ERROR = "Couldn't reach the server. Check your internet connection"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
    }
}