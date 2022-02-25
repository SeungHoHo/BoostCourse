package com.seungho.android.myapplication.movieapp.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seungho.android.myapplication.movieapp.Data.Model.Link
import com.seungho.android.myapplication.movieapp.R
import com.seungho.android.myapplication.movieapp.View.Activity.MatrixActivity

class GalleryAdapter(context: Context) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var listData = mutableListOf<Link>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //한 화면에 그려지는 아이템 개수만큼 레이아웃 생성
                val itemView =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
                return ViewHolder(itemView)
        }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //생성된 아이템 레이아웃에 값 입력 후 목록에 출력

        holder.bind(listData[position])

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView = itemView?.findViewById<ImageView>(R.id.imageView)
        private val playView = itemView?.findViewById<ImageView>(R.id.playView)

        fun bind(link: Link) {
            Glide.with(itemView.context).load(link.photoImage)
                .into(imageView)

            if (link.type == 1) {
                playView.isVisible = true
            }

            if (link.type == 0) {
                imageView.setOnClickListener {
                    val intent = Intent(itemView.context, MatrixActivity::class.java)
                    intent.putExtra("matrix", link.photoImage)
                    ContextCompat.startActivity(itemView.context, intent, null)
                }
            }

            if (link.type == 1) {
                imageView.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.videoLink))
                    ContextCompat.startActivity(itemView.context, intent, null)
                }
            }
        }

    }
}