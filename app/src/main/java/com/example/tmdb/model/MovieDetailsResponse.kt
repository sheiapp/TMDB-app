package com.example.tmdb.model


import com.example.tmdb.remote.MoviesApi
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
){
    fun getThumbnailImage(): String {
        return "${MoviesApi.IMAGE_BASE_URL}$posterPath"
    }
}