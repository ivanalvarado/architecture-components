package com.ivanalvarado.architecture_components.repository.models

data class UserDetailModel(
    val userName: String,
    val reputation: Int,
    val imageUrl: String,
    val websiteUrl: String,
    val acceptRate: Int,
    val location: String,
    val userType: String
)