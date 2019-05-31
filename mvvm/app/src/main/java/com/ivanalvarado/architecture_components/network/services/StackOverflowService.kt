package com.ivanalvarado.architecture_components.network.services

import com.ivanalvarado.architecture_components.network.models.User
import com.ivanalvarado.architecture_components.network.models.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StackOverflowService {

    @GET("users?order=desc&sort=reputation&site=stackoverflow")
    fun getUsers(): Call<UsersResponse>

    @GET("users/{id}?site=stackoverflow")
    fun getUserDetail(@Path("id") id: String): Call<User>
}