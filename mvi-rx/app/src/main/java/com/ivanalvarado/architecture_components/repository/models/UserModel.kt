package com.ivanalvarado.architecture_components.repository.models

data class UserModel(
    val userId: Int,
    val userName: String,
    val reputation: Int,
    val imageUrl: String,
    val websiteUrl: String,
    val lastAccessDate: Int
)