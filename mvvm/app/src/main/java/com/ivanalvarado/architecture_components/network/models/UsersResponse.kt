package com.ivanalvarado.architecture_components.network.models

import com.google.gson.annotations.SerializedName
import com.ivanalvarado.architecture_components.database.entity.UserDetailEntity
import com.ivanalvarado.architecture_components.database.entity.UserEntity

data class UsersResponse(
    @SerializedName("has_more") val has_more: Boolean,
    @SerializedName("items") val users: List<User>,
    @SerializedName("quota_max") val quota_max: Int,
    @SerializedName("quota_remaining") val quota_remaining: Int
)

data class User(
    @SerializedName("accept_rate") val accept_rate: Int,
    @SerializedName("account_id") val account_id: Int,
    @SerializedName("badge_counts") val badge_counts: BadgeCounts,
    @SerializedName("creation_date") val creation_date: Int,
    @SerializedName("display_name") val display_name: String,
    @SerializedName("is_employee") val is_employee: Boolean,
    @SerializedName("last_access_date") val last_access_date: Int,
    @SerializedName("last_modified_date") val last_modified_date: Int,
    @SerializedName("link") val link: String,
    @SerializedName("location") val location: String,
    @SerializedName("profile_image") val profile_image: String,
    @SerializedName("reputation") val reputation: Int,
    @SerializedName("reputation_change_day") val reputation_change_day: Int,
    @SerializedName("reputation_change_month") val reputation_change_month: Int,
    @SerializedName("reputation_change_quarter") val reputation_change_quarter: Int,
    @SerializedName("reputation_change_week") val reputation_change_week: Int,
    @SerializedName("reputation_change_year") val reputation_change_year: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("user_type") val user_type: String,
    @SerializedName("website_url") val website_url: String
) {
    fun toUserEntity(): UserEntity {
        return UserEntity(
            user_id,
            display_name,
            reputation,
            profile_image,
            website_url,
            last_access_date
        )
    }

    fun toUserDetailEntity(): UserDetailEntity {
        return UserDetailEntity(
            user_id,
            display_name,
            reputation,
            profile_image,
            website_url,
            last_access_date,
            accept_rate,
            account_id,
            creation_date,
            is_employee,
            last_modified_date,
            link,
            location,
            reputation_change_day,
            reputation_change_month,
            reputation_change_quarter,
            reputation_change_week,
            reputation_change_year,
            user_type
        )
    }
}

data class BadgeCounts(
    @SerializedName("bronze") val bronze: Int,
    @SerializedName("gold") val gold: Int,
    @SerializedName("silver") val silver: Int
)