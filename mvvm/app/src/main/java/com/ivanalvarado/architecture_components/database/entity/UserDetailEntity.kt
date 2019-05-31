package com.ivanalvarado.architecture_components.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "user_detail")
data class UserDetailEntity(
    @ColumnInfo(name = "id") @PrimaryKey @field:NonNull val id: Int,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "reputation") val reputation: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "website_url") val websiteUrl: String,
    @ColumnInfo(name = "last_access_date") val lastAccessDate: Int,
    @ColumnInfo(name = "accept_rate") val acceptRate: Int,
    @ColumnInfo(name = "account_id") val accountId: Int,
    @ColumnInfo(name = "creation_date") val creationDate: Int,
    @ColumnInfo(name = "is_employee") val isEmployee: Boolean,
    @ColumnInfo(name = "last_modified_date") val lastModifiedDate: Int,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "reputation_change_day") val reputationChangeDay: Int,
    @ColumnInfo(name = "reputation_change_month") val reputationChangeMonth: Int,
    @ColumnInfo(name = "reputation_change_quarter") val reputationChangeQuarter: Int,
    @ColumnInfo(name = "reputation_change_week") val reputationChangeWeek: Int,
    @ColumnInfo(name = "reputation_change_year") val reputationChangeYear: Int,
    @ColumnInfo(name = "user_type") val userType: String
)