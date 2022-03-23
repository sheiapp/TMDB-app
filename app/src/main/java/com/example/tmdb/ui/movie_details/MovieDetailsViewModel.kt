package com.example.tmdb.ui.movie_details

import androidx.lifecycle.ViewModel
import com.example.tmdb.data.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Shaheer cs on 22/03/2022.
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    fun getMovieDetails(movieId: Int) =
        moviesRepository.getSelectedMovieDetails(movieId)

}