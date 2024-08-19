package com.kbz.mobiz.domain.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kbz.mobiz.domain.data.vos.MovieVo
import com.kbz.mobiz.domain.data.vos.SearchVo
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchMovieDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSearchMovie( vo : SearchVo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSearchMovieWithList( voList: List<SearchVo>)

    @Query("select * from searchMovie order  BY vote_average DESC Limit :limit ")
    fun getAllMovie(limit: Int? = 20) : Flow<List<SearchVo>>
}