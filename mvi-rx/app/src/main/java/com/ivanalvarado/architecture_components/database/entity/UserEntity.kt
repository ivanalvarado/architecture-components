package com.ivanalvarado.architecture_components.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userResponses")
data class UserEntity(
    @ColumnInfo(name = "id") @PrimaryKey @field:NonNull val id: Int,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "reputation") val reputation: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "website_url") val websiteUrl: String,
    @ColumnInfo(name = "last_access_date") val lastAccessDate: Int
)