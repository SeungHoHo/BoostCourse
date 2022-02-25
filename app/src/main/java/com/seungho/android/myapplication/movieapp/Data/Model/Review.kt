package com.seungho.android.myapplication.movieapp.Data.Model

data class ReviewModel(
    var Reviews : ArrayList<Review>
)

data class Review(
    var writer: String,
    var time: String,
    var rating: Double,
    var contents: String,
    var recommend: Int )
