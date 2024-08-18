package com.kbz.mobiz.core.services.databaseService

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kbz.mobiz.domain.daos.MovieDao
import com.kbz.mobiz.domain.daos.RecentDao
import com.kbz.mobiz.domain.daos.SearchMovieDao
import com.kbz.mobiz.domain.vos.MovieVo
import com.kbz.mobiz.domain.vos.RecentVo
import com.kbz.mobiz.domain.vos.SearchVo


@Database(version = 5, entities = [MovieVo::class,SearchVo::class,RecentVo::class], exportSchema = true,
    autoMigrations = [
        AutoMigration(4,5)
    ])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract  fun movieDao() : MovieDao
    abstract  fun searchDao() : SearchMovieDao
    abstract  fun recentDao() : RecentDao

}