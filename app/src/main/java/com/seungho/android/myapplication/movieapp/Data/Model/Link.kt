package com.seungho.android.myapplication.movieapp.Data.Model

data class Link(
    /*  type
    0 = photo
    1 = video  */
    var type: Int = 0,
    var photoImage: String = "",
    var videoLink: String = ""
)
