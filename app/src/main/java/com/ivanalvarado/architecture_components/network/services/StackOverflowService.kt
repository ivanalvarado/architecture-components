package com.ivanalvarado.architecture_components.network.services

import com.ivanalvarado.architecture_components.network.UsersResponse
import retrofit2.Call
import retrofit2.http.GET

interface StackOverflowService {

    @GET("users?site=stackoverflow")
    fun getUsers(): Call<UsersResponse>
}