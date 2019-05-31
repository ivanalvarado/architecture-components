package com.ivanalvarado.architecture_components.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ivanalvarado.architecture_components.database.entity.UserDetailEntity

@Dao
interface UserDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDetail: UserDetailEntity): Long

    @Query("SELECT * FROM user_detail")
    fun getUserDetail(): List<UserDetailEntity>

    @Query("SELECT * FROM user_detail")
    fun getUserDetailStream(): LiveData<UserDetailEntity>
}