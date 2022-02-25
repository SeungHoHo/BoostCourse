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
import com.seungho.android.myapplication.movieapp.databinding.FragmentCBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentC : Fragment() {

    private var _binding: FragmentCBinding? = null
    var viewPager: ViewPagerActivity? = null
    var roomHelper: RoomHelper? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.fragmentCBtnMovieApi.setOnClickListener { viewPager?.goMovie_C_Api() }

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

                    binding.fragmentCMovieTitleTextView.text = "3. ${it?.get(2)?.title}"
                    binding.fragmentCTicketingTextView.text = "예매율 ${it?.get(2)?.reservationRate}% "
                    binding.fragmentCMovieGradeTextView.text = "| ${it?.get(2)?.grade}세 관람가 "
                    binding.fragmentCMovieDDayTextView.text = "| ${it?.get(2)?.date} 개봉"
                    Glide.with(this@FragmentC)
                        .load(it?.get(2)?.image)
                        .skipMemoryCache(true)
                        .into(binding.fragmentCMovieThumbImageView)

                    GlobalScope.launch(Dispatchers.IO) {
                        val editMovieList = RoomMovieList(3, "${it?.get(2)?.title}", "${it?.get(2)?.date}",
                            it?.get(2)?.reservationRate!!, it?.get(2)?.grade!!, "${it?.get(2)?.image}")
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
        var listData = mutableListOf<RoomMovieList>()
        GlobalScope.launch(Dispatchers.IO) {
            roomHelper?.roomMovieListDao()?.getThree()
            listData.addAll(roomHelper?.roomMovieListDao()?.getThree() ?: listOf())
            withContext(Dispatchers.Main) {
                val roomMovie = listData
                binding.fragmentCMovieTitleTextView.text = "3. ${roomMovie[0].title}"
                binding.fragmentCTicketingTextView.text = "예매율 ${roomMovie[0].reservationRate}% "
                binding.fragmentCMovieGradeTextView.text = "| ${roomMovie[0].grade}세 관람가"
                binding.fragmentCMovieDDayTextView.text = "| ${roomMovie[0].date} 개봉"
                Glide.with(this@FragmentC)
                    .load(roomMovie[0].image)
                    .skipMemoryCache(true)
                    .into(binding.fragmentCMovieThumbImageView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}