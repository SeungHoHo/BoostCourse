package com.seungho.android.myapplication.movieapp.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RoomMovieList::class), version = 1, exportSchema = false)
abstract class RoomHelper: RoomDatabase() {
    abstract fun roomMovieListDao(): RoomMovieListDao
}

@Database(entities = arrayOf(RoomMovie::class), version = 1, exportSchema = false)
abstract class RoomMovieHelper: RoomDatabase() {
    abstract fun roomMovieDao(): RoomMovieDao
}