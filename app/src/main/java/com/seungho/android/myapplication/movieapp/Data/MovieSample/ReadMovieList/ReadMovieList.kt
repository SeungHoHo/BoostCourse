package com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadMovieList


import com.google.gson.annotations.SerializedName

data class ReadMovieList(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: List<Result>?,
    @SerializedName("resultType")
    val resultType: String?
)