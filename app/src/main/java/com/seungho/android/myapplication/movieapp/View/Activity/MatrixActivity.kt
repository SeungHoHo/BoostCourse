package com.seungho.android.myapplication.movieapp.View.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.seungho.android.myapplication.movieapp.databinding.ActivityMatrixBinding

class MatrixActivity: AppCompatActivity() {

    val binding by lazy { ActivityMatrixBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val matrix = intent.getStringExtra("matrix")
        Glide.with(this)
            .load(matrix)
            .into(binding.matrixImageView)
    }
}