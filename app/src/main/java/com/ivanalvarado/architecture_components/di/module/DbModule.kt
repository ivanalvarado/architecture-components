package com.ivanalvarado.architecture_components.di.module

import android.app.Application
import com.ivanalvarado.architecture_components.database.AppDatabase
import javax.inject.Singleton
import dagger.Provides
import android.arch.persistence.room.Room
import com.ivanalvarado.architecture_components.database.dao.ExampleDao
import dagger.Module


@Module
class DbModule {

    /*
     * The method returns the Database object
     * */
    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application, AppDatabase::class.java, "Entertainment.db")
            .allowMainThreadQueries().build()
    }


    /*
     * We need the MovieDao module.
     * For this, We need the AppDatabase object
     * So we will define the providers for this here in this module.
     * */
    @Provides
    @Singleton
    internal fun provideMovieDao(appDatabase: AppDatabase): ExampleDao {
        return appDatabase.exampleDao()
    }
}