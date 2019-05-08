package com.ivanalvarado.architecture_components.repository

data class UserModel(
    val userName: String,
    val reputation: Int,
    val imageUrl: String,
    val websiteUrl: String,
    val lastAccessDate: Int
)