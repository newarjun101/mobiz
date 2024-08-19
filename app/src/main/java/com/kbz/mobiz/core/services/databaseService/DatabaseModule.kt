package com.kbz.mobiz.core.services.databaseService

import android.content.Context
import androidx.room.Room
import com.kbz.mobiz.domain.data.daos.MovieDao
import com.kbz.mobiz.domain.data.daos.RecentDao
import com.kbz.mobiz.domain.data.daos.SearchMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context : Context) :AppDatabase{
        return  Room
            .databaseBuilder(context,AppDatabase::class.java,"app_database")
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    fun provideMovieRoom(appDatabase: AppDatabase) : MovieDao = appDatabase.movieDao()

    @Provides
    fun provideSearchMovieRoom(appDatabase: AppDatabase) : SearchMovieDao = appDatabase.searchDao()

    @Provides
    fun provideRecentRoom(appDatabase: AppDatabase) : RecentDao = appDatabase.recentDao()

}