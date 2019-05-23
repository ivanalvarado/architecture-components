package com.ivanalvarado.architecture_components.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UserEntity(
    @ColumnInfo(name = "id") @PrimaryKey @field:NonNull val id: Int,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "reputation") val reputation: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "website_url") val websiteUrl: String,
    @ColumnInfo(name = "last_access_date") val lastAccessDate: Int
)