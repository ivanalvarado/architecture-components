package com.ivanalvarado.architecture_components.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ivanalvarado.architecture_components.database.entity.ExampleEntity

@Dao
interface ExampleDao {

    /* Method to insert the movies fetched from api
     * to room */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(examples: List<ExampleEntity>): LongArray

    /* Method to fetch the movies stored locally */
    @Query("SELECT * FROM `ExampleEntity`")
    fun getMoviesByPage(): List<ExampleEntity>
}