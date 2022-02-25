package com.seungho.android.myapplication.movieapp.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_movie_list")
class RoomMovieList {

    @PrimaryKey @ColumnInfo var no: Long = 0
    @ColumnInfo var title: String = ""
    @ColumnInfo var date: String = ""
    @ColumnInfo var reservationRate: Double = 0.0
    @ColumnInfo var grade: Int = 0
    @ColumnInfo var image: String = ""

    constructor(no: Long, title: String, date: String, reservationRate: Double, grade: Int, image: String) {
        this.no = no
        this.title = title
        this.date = date
        this.reservationRate = reservationRate
        this.grade = grade
        this.image = image
    }

}

@Entity(tableName = "room_movie")
class RoomMovie {

    @PrimaryKey @ColumnInfo var no: Long = 0
    @ColumnInfo var thumb: String = ""
    @ColumnInfo var title: String = ""
    @ColumnInfo var date: String = ""
    @ColumnInfo var genre: String = ""
    @ColumnInfo var duration: Int = 0
    @ColumnInfo var reservationGrade: Int = 0
    @ColumnInfo var reservationRate: Double = 0.0
    @ColumnInfo var audienceRating: Double = 0.0
    @ColumnInfo var audience: Int = 0
    @ColumnInfo var director: String = ""
    @ColumnInfo var actor: String = ""
    @ColumnInfo var synopsis: String = ""
    @ColumnInfo var like: Int = 0
    @ColumnInfo var dislike: Int = 0
    @ColumnInfo var grade: Int = 0

    constructor(no: Long, thumb: String, title: String, date: String, genre: String, duration: Int, reservationGrade: Int,
                reservationRate: Double, audienceRating: Double, audience: Int, director: String, actor: String,
                synopsis: String, like: Int, dislike: Int, grade: Int) {
        this.no = no
        this.thumb = thumb
        this.title = title
        this.date = date
        this.genre = genre
        this.duration = duration
        this.reservationGrade = reservationGrade
        this.reservationRate = reservationRate
        this.audienceRating = audienceRating
        this.audience = audience
        this.director = director
        this.actor = actor
        this.synopsis = synopsis
        this.like = like
        this.dislike = dislike
        this.grade = grade
    }


}