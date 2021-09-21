package com.ftresearch.cakes.di

import androidx.room.Room
import com.ftresearch.cakes.CakesApplication
import com.ftresearch.cakes.database.CakeDatabase
import com.ftresearch.cakes.database.dao.CakeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(application: CakesApplication): CakeDatabase {
        return Room
            .databaseBuilder(application, CakeDatabase::class.java, CakeDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAccountDao(db: CakeDatabase): CakeDao {
        return db.cakeDao()
    }
}
