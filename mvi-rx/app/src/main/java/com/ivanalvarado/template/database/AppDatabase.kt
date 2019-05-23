package com.ivanalvarado.template.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.ivanalvarado.template.database.dao.ExampleDao
import com.ivanalvarado.template.database.entity.ExampleEntity

@Database(entities = [ExampleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exampleDao(): ExampleDao
}