package com.ivanalvarado.architecture_components.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.ivanalvarado.architecture_components.database.dao.UserDao
import com.ivanalvarado.architecture_components.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exampleDao(): UserDao
}