package com.example.tmdb.ui.popular_movies_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tmdb.data.MoviesRepository
import com.example.tmdb.model.Movie
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
class PopularMoviesListViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _popularMoviesLiveDataList = MutableStateFlow<PagingData<Movie>?>(null)
    val popularMoviesLiveDataList = _popularMoviesLiveDataList.asStateFlow()

    init {
        getPopularMovieList()
    }

    private fun getPopularMovieList() = viewModelScope.launch {

        moviesRepository.getPopularMovieDataStream().cachedIn(viewModelScope).collectLatest {
            _popularMoviesLiveDataList.value = it
        }
    }


}