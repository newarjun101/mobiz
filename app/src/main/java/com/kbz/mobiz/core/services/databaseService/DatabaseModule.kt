package com.kbz.mobiz.core.services.databaseService

import android.content.Context
import androidx.room.Room
import com.kbz.mobiz.domain.daos.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule{

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context : Context) :AppDatabase{
        return  Room
            .databaseBuilder(context,AppDatabase::class.java,"app_database")
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    fun provideRoom(appDatabase: AppDatabase) : MovieDao = appDatabase.movieDao()

}