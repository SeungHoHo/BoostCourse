package com.seungho.android.myapplication.movieapp.View.Activity.ReviewList

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.seungho.android.myapplication.movieapp.Adapter.ReviewListAdapter
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadCommentList.ReadCommentList
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadMovie.ReadMovie
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadMovie.Result
import com.seungho.android.myapplication.movieapp.Api.ApiObject
import com.seungho.android.myapplication.movieapp.R
import com.seungho.android.myapplication.movieapp.Room.RoomMovie
import com.seungho.android.myapplication.movieapp.Room.RoomMovieHelper
import com.seungho.android.myapplication.movieapp.databinding.ActivityReviewListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReviewListBActivity: AppCompatActivity() {
    private val binding by lazy { ActivityReviewListBinding.inflate(layoutInflater) }
    var roomHelper: RoomMovieHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        roomHelper = Room.databaseBuilder(applicationContext, RoomMovieHelper::class.java, "room_movie")
            .build()

        loadMovie()
        loadComment()

    }

    private fun setAdapter(ReviewList: List<com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadCommentList.Result>?) {
        val mAdapter = ReviewListAdapter(ReviewList, ReviewListAActivity())
        binding.reviewRecyclerView.adapter = mAdapter
        binding.reviewRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadMovie() {
        val movie_id = "2"

        val call = ApiObject.movieListOpenService.getMovie(movie_id)

        call.enqueue(object : Callback<ReadMovie> {
            override fun onResponse(call: Call<ReadMovie>, response: Response<ReadMovie>) {
                if (response.isSuccessful) {
                    Log.e(">>", "레트로핏 성공")
                    val it: List<Result>? = response.body()!!.result
                    binding.movieTitleTextView.text = "${it?.get(0)?.title}"
                    val rating = it?.get(0)?.reservationRate.toString()
                    binding.ratingBar.rating = rating.toFloat()
                    binding.ratingTextView.text = "${it?.get(0)?.audienceRating}"
                    when (it?.get(0)?.grade) {
                        12 -> Glide.with(this@ReviewListBActivity)
                            .load(R.drawable.ic_12)
                            .into(binding.movieGradeImageView)
                        15 -> Glide.with(this@ReviewListBActivity)
                            .load(R.drawable.ic_15)
                            .into(binding.movieGradeImageView)
                        19 -> Glide.with(this@ReviewListBActivity)
                            .load(R.drawable.ic_19)
                            .into(binding.movieGradeImageView)
                    }

                }
            }

            override fun onFailure(call: Call<ReadMovie>, t: Throwable) {
                loadRoom()
            }

        })
    }

    private fun loadComment() {
        val id = "2"

        val call = ApiObject.movieListOpenService.getList(id)

        call.enqueue(object : Callback<ReadCommentList> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<ReadCommentList>, response: Response<ReadCommentList>) {
                if (response.isSuccessful) {
                    Log.e(">>>>", "코멘트 레트로핏 성공")
                    val body = response.body()
                    body?.let {
                        setAdapter(it.result)
                    }
                    binding.textView1.text = "(${body?.totalCount.toString()}명 참여)"
                }

            }

            override fun onFailure(call: Call<ReadCommentList>, t: Throwable) {
                Log.e(">>>>", "코멘트 레트로핏 실패")
                loadComment()
            }

        })

    }

    @SuppressLint("SetTextI18n")
    private fun loadRoom() {
        val listData = mutableListOf<RoomMovie>()
        GlobalScope.launch(Dispatchers.IO) {
            roomHelper?.roomMovieDao()?.getTwo()
            listData.addAll(roomHelper?.roomMovieDao()?.getTwo() ?: listOf())
            withContext(Dispatchers.Main) {
                val roomMovie = listData
                binding.movieTitleTextView.text = roomMovie[0].title //제목
                val rating = roomMovie[0].audienceRating.toString()
                binding.ratingBar.rating = rating.toFloat() //평점
                binding.ratingTextView.text = "${roomMovie[0].audienceRating}"
                when (roomMovie[0].grade) {
                    12 -> Glide.with(this@ReviewListBActivity)
                        .load(R.drawable.ic_12)
                        .into(binding.movieGradeImageView)
                    15 -> Glide.with(this@ReviewListBActivity)
                        .load(R.drawable.ic_15)
                        .into(binding.movieGradeImageView)
                    19 -> Glide.with(this@ReviewListBActivity)
                        .load(R.drawable.ic_19)
                        .into(binding.movieGradeImageView)
                }
            }
        }
    }
}