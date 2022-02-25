package com.seungho.android.myapplication.movieapp.Api

import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadCommentList.ReadCommentList
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadMovie.ReadMovie
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadMovieList.ReadMovieList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RetrofitService {

    companion object {
        const val MOVIE_URL = "http://boostcourse-appapi.connect.or.kr:10000/"
    }
}

interface MovieListOpenService {

    @GET("movie/readMovieList?type=1")
    fun getMovieList(): Call<ReadMovieList>
    //http://boostcourse-appapi.connect.or.kr:10000/movie/readMovie?&id=1
    @GET("movie/readMovie?")
    fun getMovie(@Query("id") id: String): Call<ReadMovie>
    //http://boostcourse-appapi.connect.or.kr:10000/movie/readCommentList?&id=1&limit=4
    @GET("movie/readCommentList?")
    fun getList(@Query("id") id: String): Call<ReadCommentList>
    @GET("movie/readCommentList?")
    fun getListLimit(@Query("id") id: String,
                    @Query("limit") limit: String): Call<ReadCommentList>
}

private val retrofit = Retrofit.Builder()
    .baseUrl(RetrofitService.MOVIE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object ApiObject {
    val movieListOpenService: MovieListOpenService by lazy {
        retrofit.create(MovieListOpenService::class.java)
    }
}