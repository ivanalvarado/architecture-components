package com.ivanalvarado.architecture_components.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ivanalvarado.architecture_components.database.entity.UserDetailEntity

@Dao
interface UserDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDetail: UserDetailEntity): Long

    @Query("SELECT EXISTS(SELECT 1 FROM user_detail WHERE id = :id)")
    fun hasUserDetail(id: String): Boolean

    @Query("SELECT * FROM user_detail WHERE id = :id")
    fun getUserDetail(id: String): List<UserDetailEntity>

    @Query("SELECT * FROM user_detail WHERE id = :id")
    fun getUserDetailStream(id: String): LiveData<UserDetailEntity>
}