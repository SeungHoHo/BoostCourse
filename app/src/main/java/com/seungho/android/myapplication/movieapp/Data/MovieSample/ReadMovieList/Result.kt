package com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadMovieList


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("audience_rating")
    val audienceRating: Double?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("grade")
    val grade: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("reservation_grade")
    val reservationGrade: Int?,
    @SerializedName("reservation_rate")
    val reservationRate: Double?,
    @SerializedName("reviewer_rating")
    val reviewerRating: Double?,
    @SerializedName("thumb")
    val thumb: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("title_eng")
    val titleEng: String?,
    @SerializedName("user_rating")
    val userRating: Double?
)