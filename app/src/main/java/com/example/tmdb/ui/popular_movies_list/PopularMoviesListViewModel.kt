package com.example.tmdb.ui.popular_movies_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tmdb.data.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Shaheer cs on 22/03/2022.
 */
@HiltViewModel
class PopularMoviesListViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {


    fun getPopularMovieList() = moviesRepository.getSearchResultStream().cachedIn(viewModelScope).asLiveData()

}