package com.seungho.android.myapplication.movieapp.View.Fragment.MovieList

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadMovieList.ReadMovieList
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadMovieList.Result
import com.seungho.android.myapplication.movieapp.View.Activity.ViewPagerActivity
import com.seungho.android.myapplication.movieapp.Api.ApiObject
import com.seungho.android.myapplication.movieapp.Room.RoomHelper
import com.seungho.android.myapplication.movieapp.Room.RoomMovieList
import com.seungho.android.myapplication.movieapp.databinding.FragmentABinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentA : Fragment() {
    private var _binding: FragmentABinding? = null
    var viewPager: ViewPagerActivity? = null
    var roomHelper: RoomHelper? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentABinding.inflate(inflater, container, false)
        val view = binding.root
        binding.fragmentABtnMovieApi.setOnClickListener { viewPager?.goMovie_A_Api() }

        roomHelper = Room.databaseBuilder(requireContext().applicationContext, RoomHelper::class.java, "room_movie_list")
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            roomHelper?.roomMovieListDao()?.getAll()
        }

        loadMovieList()

        return view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ViewPagerActivity) viewPager = context
    }

    private fun loadMovieList() {
        val call = ApiObject.movieListOpenService.getMovieList()

        call.enqueue(object : Callback<ReadMovieList> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<ReadMovieList>, response: Response<ReadMovieList>) {
                if (response.isSuccessful) {
                    val it: List<Result>? = response.body()!!.result

                    val title = "1. ${it?.get(0)?.title}"
                    val reservationRate = "예매율 ${it?.get(0)?.reservationRate}% "
                    val grade = "| ${it?.get(0)?.grade}세 관람가 "
                    val date = "| ${it?.get(0)?.date} 개봉"
                    
                    binding.fragmentAMovieTitleTextView.text = title
                    binding.fragmentATicketingTextView.text = reservationRate
                    binding.fragmentAMovieGradeTextView.text = grade
                    binding.fragmentAMovieDDayTextView.text = date
                    Glide.with(this@FragmentA)
                        .load(it?.get(0)?.image)
                        .skipMemoryCache(true)
                        .into(binding.fragmentAMovieThumbImageView)


                    GlobalScope.launch(Dispatchers.IO) {
                        val editMovieList = RoomMovieList(1, "${it?.get(0)?.title}", "${it?.get(0)?.date}",
                            it?.get(0)?.reservationRate!!, it?.get(0)?.grade!!, "${it?.get(0)?.image}")
                        roomHelper?.roomMovieListDao()?.insert(editMovieList)
                    }
                }
            }

            override fun onFailure(call: Call<ReadMovieList>, t: Throwable) {
                loadRoom()
            }

        })
    }

    @SuppressLint("SetTextI18n")
    private fun loadRoom() {
        val listData = mutableListOf<RoomMovieList>()
        GlobalScope.launch(Dispatchers.IO) {
            roomHelper?.roomMovieListDao()?.getOne()
            listData.addAll(roomHelper?.roomMovieListDao()?.getOne() ?: listOf())
            withContext(Dispatchers.Main) {
                val roomMovie = listData
                binding.fragmentAMovieTitleTextView.text = "1. ${roomMovie[0].title}"
                binding.fragmentATicketingTextView.text = "예매율 ${roomMovie[0].reservationRate}% "
                binding.fragmentAMovieGradeTextView.text = "| ${roomMovie[0].grade}세 관람가"
                binding.fragmentAMovieDDayTextView.text = "| ${roomMovie[0].date} 개봉"
                Glide.with(this@FragmentA)
                    .load(roomMovie[0].image)
                    .skipMemoryCache(true)
                    .into(binding.fragmentAMovieThumbImageView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}