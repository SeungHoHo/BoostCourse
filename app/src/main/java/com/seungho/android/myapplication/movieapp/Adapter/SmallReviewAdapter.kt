package com.seungho.android.myapplication.movieapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadCommentList.Result
import com.seungho.android.myapplication.movieapp.View.Fragment.Movie.MovieAActivity
import com.seungho.android.myapplication.movieapp.R
import com.seungho.android.myapplication.movieapp.Room.RoomHelper
import com.seungho.android.myapplication.movieapp.Room.RoomMovieList


class SmallReviewAdapter(private val commentList: List<Result>?, val context: MovieAActivity) : RecyclerView.Adapter<SmallReviewAdapter.ViewHolder>() {

    var roomHelper: RoomHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentList?.get(position),context)
    }

    override fun getItemCount(): Int {
        return commentList!!.size
    }

    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {

        private val writer = itemView?.findViewById<TextView>(R.id.nicknameTextView)
        private val time = itemView?.findViewById<TextView>(R.id.timeTextView)
        val rating = itemView?.findViewById<RatingBar>(R.id.ratingBarId)
        private val contents = itemView?.findViewById<TextView>(R.id.descriptionTextView)
        private val recommend = itemView?.findViewById<TextView>(R.id.recommendTextView)

        fun bind(itemReview: Result?, context: MovieAActivity) {
            writer?.text = itemReview?.writer
            time?.text = itemReview?.time
            rating?.rating = itemReview?.rating!!.toFloat()
            contents?.text = itemReview.contents
            recommend?.text = itemReview.recommend.toString()
        }

    }
}