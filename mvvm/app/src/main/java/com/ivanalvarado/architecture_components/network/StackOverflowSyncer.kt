package com.ivanalvarado.architecture_components.network

import com.ivanalvarado.architecture_components.database.dao.UserDao
import com.ivanalvarado.architecture_components.database.dao.UserDetailDao
import com.ivanalvarado.architecture_components.network.models.User
import com.ivanalvarado.architecture_components.network.models.UsersResponse
import com.ivanalvarado.architecture_components.network.services.StackOverflowService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class StackOverflowSyncer @Inject constructor(
    private val stackOverflowService: StackOverflowService,
    private val usersDao: UserDao,
    private val userDetailDao: UserDetailDao
) {

    fun refreshUsers() {
        stackOverflowService.getUsers().enqueue(object : Callback<UsersResponse> {
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                TODO("Failure for getUsers() not implemented")
            }

            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    users?.users?.forEach {
                        usersDao.insert(it.toUserEntity())
                    }
                } else {
                    TODO("Unsuccessful response not implemented")
                }
            }
        })
    }

    fun refreshUserDetail(userId: String) {
        stackOverflowService.getUserDetail(userId).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Failure for getUserDetail() not implemented")
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val userDetail = response.body()
                    userDetail?.let { userDetailDao.insert(userDetail.toUserDetailEntity()) }
                } else {
                    TODO("Unsuccessful response not implemented")
                }
            }
        })
    }
}