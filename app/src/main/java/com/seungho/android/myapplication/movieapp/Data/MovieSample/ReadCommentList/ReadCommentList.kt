package com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadCommentList


import com.google.gson.annotations.SerializedName

data class ReadCommentList(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: List<Result>?,
    @SerializedName("resultType")
    val resultType: String?,
    @SerializedName("totalCount")
    val totalCount: Int?
)