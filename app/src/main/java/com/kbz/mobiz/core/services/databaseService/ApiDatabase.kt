package com.kbz.mobiz.core.services.databaseService

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kbz.mobiz.domain.data.daos.MovieDao
import com.kbz.mobiz.domain.data.daos.RecentDao
import com.kbz.mobiz.domain.data.daos.SearchMovieDao
import com.kbz.mobiz.domain.data.vos.MovieVo
import com.kbz.mobiz.domain.data.vos.RecentVo
import com.kbz.mobiz.domain.data.vos.SearchVo


@Database(version = 5, entities = [MovieVo::class, SearchVo::class, RecentVo::class], exportSchema = true,
    autoMigrations = [
        AutoMigration(4,5)
    ])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract  fun movieDao() : MovieDao
    abstract  fun searchDao() : SearchMovieDao
    abstract  fun recentDao() : RecentDao

}