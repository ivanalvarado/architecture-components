package com.ivanalvarado.architecture_components.network

import com.ivanalvarado.architecture_components.database.dao.UserDao
import com.ivanalvarado.architecture_components.database.dao.UserDetailDao
import com.ivanalvarado.architecture_components.network.models.UsersResponse
import com.ivanalvarado.architecture_components.network.services.StackOverflowService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

class StackOverflowSyncer @Inject constructor(
    private val stackOverflowService: StackOverflowService,
    private val usersDao: UserDao,
    private val executor: Executor,
    private val userDetailDao: UserDetailDao
) {
    private val TAG = StackOverflowSyncer::class.java.simpleName

    fun refreshUsers() {
        stackOverflowService.getUsers().enqueue(object : Callback<UsersResponse> {
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                TODO("Failure for getUserResponses() not implemented")
            }

            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    users?.userResponses?.forEach {
                        usersDao.insert(it.toUserEntity())
                    }
                } else {
                    TODO("Unsuccessful response not implemented")
                }
            }
        })
    }

    fun refreshUserDetail(userId: String, forceRefresh: Boolean) {

        executor.execute {
            val userExists = userDetailDao.hasUserDetail(userId /* TODO("Add timestamp in milliseconds") */)

            if (!userExists || forceRefresh) {
                val response = stackOverflowService.getUserDetail(userId).execute()

                if (response.isSuccessful) {
                    val userDetail = response.body()
                    userDetail?.userResponses?.let {
                        userDetailDao.insert(it[0].toUserDetailEntity())
                    }
                } else {
                    TODO("Unsuccessful response not implemented")
                }
            }
        }
    }
}