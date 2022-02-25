package com.seungho.android.myapplication.movieapp.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface RoomMovieListDao {

    @Query("select * from room_movie_list")
    suspend fun getAll(): List<RoomMovieList>

    @Query("select * from room_movie_list where `no`=1 ")
    suspend fun getOne(): List<RoomMovieList>

    @Query("select * from room_movie_list where `no`=2 ")
    suspend fun getTwo(): List<RoomMovieList>

    @Query("select * from room_movie_list where `no`=3 ")
    suspend fun getThree(): List<RoomMovieList>

    @Query("select * from room_movie_list where `no`=4 ")
    suspend fun getFour(): List<RoomMovieList>

    @Query("select * from room_movie_list where `no`=5 ")
    suspend fun getFive(): List<RoomMovieList>

    @Insert(onConflict = IGNORE)
    suspend fun insert(movieList: RoomMovieList)

    @Delete
    suspend fun delete(movieList: RoomMovieList)

}

@Dao
interface RoomMovieDao {

    @Query("select * from room_movie")
    suspend fun getAll(): List<RoomMovie>

    @Query("select * from room_movie where `no`=1 ")
    suspend fun getOne(): List<RoomMovie>

    @Query("select * from room_movie where `no`=2 ")
    suspend fun getTwo(): List<RoomMovie>

    @Query("select * from room_movie where `no`=3 ")
    suspend fun getThree(): List<RoomMovie>

    @Query("select * from room_movie where `no`=4 ")
    suspend fun getFour(): List<RoomMovie>

    @Query("select * from room_movie where `no`=5 ")
    suspend fun getFive(): List<RoomMovie>

    @Insert(onConflict = IGNORE)
    suspend fun insert(movie: RoomMovie)

    @Delete
    suspend fun delete(movie: RoomMovie)
}