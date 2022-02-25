package com.seungho.android.myapplication.movieapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seungho.android.myapplication.movieapp.Data.MovieSample.ReadCommentList.Result
import com.seungho.android.myapplication.movieapp.R
import com.seungho.android.myapplication.movieapp.View.Activity.ReviewList.ReviewListAActivity

class ReviewListAdapter(private val commentList: List<Result>?, val context: ReviewListAActivity) : RecyclerView.Adapter<ReviewListAdapter.ViewHolder>() {

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

    class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {

        val writer = itemView?.findViewById<TextView>(R.id.nicknameTextView)
        val time = itemView?.findViewById<TextView>(R.id.timeTextView)
        val rating = itemView?.findViewById<RatingBar>(R.id.ratingBarId)
        val contents = itemView?.findViewById<TextView>(R.id.descriptionTextView)
        val recommend = itemView?.findViewById<TextView>(R.id.recommendTextView)

        fun bind(itemReview: Result?, context: ReviewListAActivity) {
            writer?.text = itemReview?.writer
            time?.text = itemReview?.time
            rating?.rating = itemReview?.rating!!.toFloat()
            contents?.text = itemReview.contents
            recommend?.text = itemReview.recommend.toString()
        }

    }
}