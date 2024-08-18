package com.kbz.mobiz.domain.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kbz.mobiz.domain.vos.MovieVo
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie( popularMovieVo: MovieVo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieWithList( popularMovieVo: List<MovieVo>)

    @Query("select * from popularMovie")
    fun getAllMovie() : Flow<List<MovieVo>>
}