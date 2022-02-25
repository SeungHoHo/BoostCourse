package com.seungho.android.myapplication.movieapp.View.Fragment.MovieList

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.seungho.android.myapplication.movieapp.databinding.FragmentBBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentB : Fragment() {

    private var _binding: FragmentBBinding? = null
    var viewPager: ViewPagerActivity? = null
    var roomHelper: RoomHelper? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.fragmentBBtnMovieApi.setOnClickListener { viewPager?.goMovie_B_Api() }

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

            override fun onResponse(call: Call<ReadMovieList>, response: Response<ReadMovieList>) {
                if (response.isSuccessful) {
                    val it: List<Result>? = response.body()!!.result

                    binding.fragmentBMovieTitleTextView.text = "2. ${it?.get(1)?.title}"
                    binding.fragmentBTicketingTextView.text = "예매율 ${it?.get(1)?.reservationRate}% "
                    binding.fragmentBMovieGradeTextView.text = "| ${it?.get(1)?.grade}세 관람가 "
                    binding.fragmentBMovieDDayTextView.text = "| ${it?.get(1)?.date} 개봉"
                    Glide.with(this@FragmentB)
                        .load(it?.get(1)?.image)
                        .skipMemoryCache(true)
                        .into(binding.fragmentBMovieThumbImageView)

                    GlobalScope.launch(Dispatchers.IO) {
                        val editMovieList = RoomMovieList(2, "${it?.get(1)?.title}", "${it?.get(1)?.date}",
                            it?.get(1)?.reservationRate!!, it?.get(1)?.grade!!, "${it?.get(1)?.image}")
                        roomHelper?.roomMovieListDao()?.insert(editMovieList)
                    }
                }
            }

            override fun onFailure(call: Call<ReadMovieList>, t: Throwable) {
                Log.e(">>", "레트로핏 실패")
                loadRoom()

            }

        })
    }

    @SuppressLint("SetTextI18n")
    private fun loadRoom() {
        var listData = mutableListOf<RoomMovieList>()
        GlobalScope.launch(Dispatchers.IO) {
            roomHelper?.roomMovieListDao()?.getTwo()
            listData.addAll(roomHelper?.roomMovieListDao()?.getTwo() ?: listOf())
            withContext(Dispatchers.Main) {
                val roomMovie = listData
                binding.fragmentBMovieTitleTextView.text = "2. ${roomMovie[0].title}"
                binding.fragmentBTicketingTextView.text = "예매율 ${roomMovie[0].reservationRate}% "
                binding.fragmentBMovieGradeTextView.text = "| ${roomMovie[0].grade}세 관람가"
                binding.fragmentBMovieDDayTextView.text = "| ${roomMovie[0].date} 개봉"
                Glide.with(this@FragmentB)
                    .load(roomMovie[0].image)
                    .skipMemoryCache(true)
                    .into(binding.fragmentBMovieThumbImageView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}