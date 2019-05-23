package com.ivanalvarado.architecture_components.network

import android.util.Log
import com.ivanalvarado.architecture_components.database.dao.UserDao
import com.ivanalvarado.architecture_components.network.services.StackOverflowService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class StackOverflowSyncer @Inject constructor(
    private val stackOverflowService: StackOverflowService,
    private val usersDao: UserDao
) {

    fun refreshUsers() {
        stackOverflowService.getUsers().enqueue(object : Callback<UsersResponse> {
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                TODO("Failure for getUsers() not implemented") //To change body of created functions use File | Settings | File Templates.
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
}