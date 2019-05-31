package com.ivanalvarado.architecture_components.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.ivanalvarado.architecture_components.database.dao.UserDao
import com.ivanalvarado.architecture_components.database.dao.UserDetailDao
import com.ivanalvarado.architecture_components.database.entity.UserDetailEntity
import com.ivanalvarado.architecture_components.database.entity.UserEntity

@Database(entities = [UserEntity::class, UserDetailEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun userDetailDao(): UserDetailDao
}