package com.example.tmdb.ui.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MoviesRepository
import com.example.tmdb.model.MovieDetailsResponse
import com.example.tmdb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Shaheer cs on 22/03/2022.
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _movieDetails = MutableStateFlow<Result<MovieDetailsResponse?>?>(null)
    val movieDetails get() = _movieDetails.asStateFlow()

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        if (movieId != 0)
            moviesRepository.getSelectedMovieDetails(movieId).collectLatest {
                _movieDetails.value = it
            }
    }

}