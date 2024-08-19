package com.kbz.mobiz.domain.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kbz.mobiz.domain.data.vos.MovieVo
import com.kbz.mobiz.domain.data.vos.RecentVo
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecent( recentVo: RecentVo)
    @Delete
    fun deleteRecent(model: RecentVo)
    @Query("select * from recent ORDER BY time_stemp DESC limit :limit")
    fun getAllRecentKeywords(limit : Int? = 30) : Flow<List<RecentVo>>
}