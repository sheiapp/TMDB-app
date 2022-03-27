package com.example.tmdb.model


import com.example.tmdb.remote.MoviesApi.Companion.IMAGE_BASE_URL
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
) {
    fun getThumbnailImage(): String {
        return "$IMAGE_BASE_URL$posterPath"
    }
}