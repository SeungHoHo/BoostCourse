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
import com.seungho.android.myapplication.movieapp.databinding.FragmentEBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentE : Fragment() {

    private var _binding: FragmentEBinding? = null
    var viewPager: ViewPagerActivity? = null
    var roomHelper: RoomHelper? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.fragmentEBtnMovieApi.setOnClickListener { viewPager?.goMovie_E_Api() }

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

                    binding.fragmentEMovieTitleTextView.text = "5. ${it?.get(4)?.title}"
                    binding.fragmentETicketingTextView.text = "예매율 ${it?.get(4)?.reservationRate}% "
                    binding.fragmentEMovieGradeTextView.text = "| ${it?.get(4)?.grade}세 관람가 "
                    binding.fragmentEMovieDDayTextView.text = "| ${it?.get(4)?.date} 개봉"
                    Glide.with(this@FragmentE)
                        .load(it?.get(4)?.image)
                        .skipMemoryCache(true)
                        .into(binding.fragmentEMovieThumbImageView)

                    GlobalScope.launch(Dispatchers.IO) {
                        val editMovieList = RoomMovieList(5, "${it?.get(4)?.title}", "${it?.get(4)?.date}",
                            it?.get(4)?.reservationRate!!, it?.get(4)?.grade!!, "${it?.get(4)?.image}")
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
            roomHelper?.roomMovieListDao()?.getFive()
            listData.addAll(roomHelper?.roomMovieListDao()?.getFive() ?: listOf())
            withContext(Dispatchers.Main) {
                val roomMovie = listData
                binding.fragmentEMovieTitleTextView.text = "5. ${roomMovie[0].title}"
                binding.fragmentETicketingTextView.text = "예매율 ${roomMovie[0].reservationRate}% "
                binding.fragmentEMovieGradeTextView.text = "| ${roomMovie[0].grade}세 관람가"
                binding.fragmentEMovieDDayTextView.text = "| ${roomMovie[0].date} 개봉"
                Glide.with(this@FragmentE)
                    .load(roomMovie[0].image)
                    .skipMemoryCache(true)
                    .into(binding.fragmentEMovieThumbImageView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}