package com.kbz.mobiz.core.services.databaseService

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kbz.mobiz.domain.daos.MovieDao
import com.kbz.mobiz.domain.vos.MovieVo


@Database(version = 2, entities = [MovieVo::class], exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract  fun movieDao() : MovieDao

}