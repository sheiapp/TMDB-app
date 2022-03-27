package com.example.tmdb.model


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name")
    val name: String
)