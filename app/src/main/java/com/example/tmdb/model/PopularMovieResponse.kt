package com.example.tmdb.model


import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    @SerializedName("results")
    val movies: List<Movie>
)