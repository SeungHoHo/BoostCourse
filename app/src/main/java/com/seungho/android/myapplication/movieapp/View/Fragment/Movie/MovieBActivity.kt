package com.seungho.android.myapplication.movieapp.View.Fragment.Movie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.seungho.android.myapplication.movieapp.Adapter.SmallReviewAdapter
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadCommentList.ReadCommentList
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadMovie.ReadMovie
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadMovie.Result
import com.seungho.android.myapplication.movieapp.R
import com.seungho.android.myapplication.movieapp.View.Activity.ReviewList.ReviewListBActivity
import com.seungho.android.myapplication.movieapp.Api.ApiObject
import com.seungho.android.myapplication.movieapp.Room.RoomMovie
import com.seungho.android.myapplication.movieapp.Room.RoomMovieHelper
import com.seungho.android.myapplication.movieapp.databinding.ActivityMovieBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieBActivity : Fragment() {
    private var _binding: ActivityMovieBinding? = null
    var roomHelper: RoomMovieHelper? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = ActivityMovieBinding.inflate(inflater, container, false)
        val view = binding.root

        roomHelper = Room.databaseBuilder(requireContext().applicationContext, RoomMovieHelper::class.java, "room_movie")
            .build()

        loadMovie()
        loadComment()

        binding.btnAllReview.setOnClickListener {
            allReview()
        }

        return view
    }

    private fun loadMovie() {
        // ?????? ?????? ?????????
        val movie_id = "2"

        val call = ApiObject.movieListOpenService.getMovie(movie_id)

        call.enqueue(object : Callback<ReadMovie> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<ReadMovie>, response: Response<ReadMovie>) {
                if (response.isSuccessful) {
                    val it: List<Result>? = response.body()!!.result
                    binding.movieTitleTextView.text = "${it?.get(0)?.title}"
                    Glide.with(this@MovieBActivity)
                        .load(it?.get(0)?.thumb)
                        .skipMemoryCache(true)
                        .into(binding.movieThumbImageView)
                    binding.movieOpenDtTextView.text= "${it?.get(0)?.date} ??????"
                    binding.movieGenreTextView.text = "${it?.get(0)?.genre}"
                    binding.movieTimeTextView.text = "${it?.get(0)?.duration}???"
                    binding.movieReservationTextView.text = "${it?.get(0)?.reservationGrade}??? ${it?.get(0)?.reservationRate}%\n"
                    val rating = it?.get(0)?.audienceRating.toString()
                    binding.ratingBar.rating = rating.toFloat()
                    binding.ratingTextVIew.text = "${it?.get(0)?.audienceRating}"
                    binding.audienceTextView.text = "${it?.get(0)?.audience}???\n"
                    binding.movieDirectorTextView.text = "${it?.get(0)?.director}"
                    binding.movieStarTextView.text = "${it?.get(0)?.actor}"
                    binding.synopsisTextView.text = "${it?.get(0)?.synopsis}"
                    binding.likeTextView.text = "${it?.get(0)?.like}"
                    binding.unlikeTextView.text = "${it?.get(0)?.dislike}"
                    when (it?.get(0)?.grade) {
                        12 -> Glide.with(this@MovieBActivity)
                            .load(R.drawable.ic_12)
                            .into(binding.movieGradeImageView)
                        15 -> Glide.with(this@MovieBActivity)
                            .load(R.drawable.ic_15)
                            .into(binding.movieGradeImageView)
                        19 -> Glide.with(this@MovieBActivity)
                            .load(R.drawable.ic_19)
                            .into(binding.movieGradeImageView)
                    }

                    var likeCountText = it?.get(0)?.like
                    var unlikeCountText = it?.get(0)?.dislike
                    var likeState = false // ????????? ??????
                    var unlikeState = false // ????????? ??????

                    binding.btnLike.setOnClickListener {
                        if (likeState == false) {
                            if (unlikeState) {
                                unlikeCountText = unlikeCountText?.minus(1)
                                binding.unlikeTextView.text = unlikeCountText.toString()
                                binding.btnUnlike.setBackgroundResource(R.drawable.ic_thumb_down)
                                unlikeState = false
                                // ????????? ????????? ?????? ????????? ???????????? ????????? ????????? ????????? ????????? ????????? ?????????
                            }
                            likeCountText = likeCountText?.plus(1)
                            binding.likeTextView.text = likeCountText.toString()
                            binding.btnLike.setBackgroundResource(R.drawable.ic_thumb_up_selected)
                            likeState = true
                            // ????????? ????????? ????????? ????????? ???????????? ???????????? ??????
                        }

                        else if (likeState) {
                            likeCountText = likeCountText?.minus(1)
                            binding.likeTextView.text = likeCountText.toString()
                            binding.btnLike.setBackgroundResource(R.drawable.ic_thumb_up)
                            likeState = false
                            // ???????????? ?????? ??? ????????? ?????????
                        }
                    }

                    binding.btnUnlike.setOnClickListener {
                        if (unlikeState == false) {
                            if (likeState) {
                                likeCountText = likeCountText?.minus(1)
                                binding.likeTextView.text = likeCountText.toString()
                                binding.btnLike.setBackgroundResource(R.drawable.ic_thumb_up)
                                likeState = false
                                // ????????? ????????? ?????? ????????? ???????????? ????????? ????????? ????????? ????????? ????????? ?????????
                            }
                            unlikeCountText = unlikeCountText?.plus(1)
                            binding.unlikeTextView.text = unlikeCountText.toString()
                            binding.btnUnlike.setBackgroundResource(R.drawable.ic_thumb_down_selected)
                            unlikeState = true
                            // ????????? ????????? ????????? ????????? ???????????? ???????????? ??????
                        }
                        else if (unlikeState) {
                            unlikeCountText = unlikeCountText?.minus(1)
                            binding.unlikeTextView.text = unlikeCountText.toString()
                            binding.btnUnlike.setBackgroundResource(R.drawable.ic_thumb_down)
                            unlikeState = false
                            // ???????????? ?????? ??? ????????? ?????????
                        }
                    }

                    GlobalScope.launch(Dispatchers.IO) {
                        val editMovie = RoomMovie(2, "${it?.get(0)?.thumb}", "${it?.get(0)?.title}",
                            "${it?.get(0)?.date}", "${it?.get(0)?.genre}", it?.get(0)?.duration!!, it[0].reservationGrade!!,
                            it[0].reservationRate!!, it[0].audienceRating!!, it[0].audience!!, "${it[0].director}",
                            "${it[0].actor}", "${it[0].synopsis}", it[0].like!!, it[0].dislike!!, it[0].grade!!)
                        roomHelper?.roomMovieDao()?.insert(editMovie)
                    }

                }
            }

            override fun onFailure(call: Call<ReadMovie>, t: Throwable) {
                loadRoom()
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
                binding.movieTitleTextView.text = roomMovie[0].title
                Glide.with(this@MovieBActivity)
                    .load(roomMovie[0].thumb)
                    .skipMemoryCache(true)
                    .into(binding.movieThumbImageView)
                binding.movieOpenDtTextView.text= "${roomMovie[0].date} ??????"
                binding.movieGenreTextView.text = roomMovie[0].genre
                binding.movieTimeTextView.text = "${roomMovie[0].duration}???"
                binding.movieReservationTextView.text = "${roomMovie[0].reservationGrade}??? ${roomMovie[0].reservationRate}%\n"
                val rating = roomMovie[0].audienceRating.toString()
                binding.ratingBar.rating = rating.toFloat()
                binding.ratingTextVIew.text = "${roomMovie[0].audienceRating}"
                binding.audienceTextView.text = "${roomMovie[0].audience}???\n"
                binding.movieDirectorTextView.text = roomMovie[0].director
                binding.movieStarTextView.text = roomMovie[0].actor
                binding.synopsisTextView.text = roomMovie[0].synopsis
                binding.likeTextView.text = "${roomMovie[0].like}"
                binding.unlikeTextView.text = "${roomMovie[0].dislike}"
                when (roomMovie[0].grade) {
                    12 -> Glide.with(this@MovieBActivity)
                        .load(R.drawable.ic_12)
                        .into(binding.movieGradeImageView)
                    15 -> Glide.with(this@MovieBActivity)
                        .load(R.drawable.ic_15)
                        .into(binding.movieGradeImageView)
                    19 -> Glide.with(this@MovieBActivity)
                        .load(R.drawable.ic_19)
                        .into(binding.movieGradeImageView)
                }
            }
        }
    }

    private fun setAdapter(ReviewList: List<com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadCommentList.Result>?) {
        val mAdapter = SmallReviewAdapter(ReviewList, MovieAActivity())
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun loadComment() {
        //?????? ?????????
        val id = "2"
        val limit = "4"

        val call = ApiObject.movieListOpenService.getListLimit(id, limit)

        call.enqueue(object : Callback<ReadCommentList>{
            override fun onResponse(call: Call<ReadCommentList>, response: Response<ReadCommentList>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        setAdapter(it.result)
                    }

                }
            }

            override fun onFailure(call: Call<ReadCommentList>, t: Throwable) {
                loadComment()
            }

        })

    }

    fun allReview() {
        val intent = Intent(context, ReviewListBActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}