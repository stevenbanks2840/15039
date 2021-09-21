package com.ftresearch.cakes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ftresearch.cakes.database.dao.CakeDao
import com.ftresearch.cakes.database.entity.CakeEntity

@Database(entities = [CakeEntity::class], version = 1)
abstract class CakeDatabase : RoomDatabase() {

    abstract fun cakeDao(): CakeDao

    companion object {

        const val DATABASE_NAME: String = "cakes_db"
    }
}
