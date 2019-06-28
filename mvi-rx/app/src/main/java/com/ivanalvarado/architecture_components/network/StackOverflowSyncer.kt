package com.ivanalvarado.architecture_components.network

import android.annotation.SuppressLint
import android.util.Log
import com.ivanalvarado.architecture_components.database.dao.UserDao
import com.ivanalvarado.architecture_components.database.dao.UserDetailDao
import com.ivanalvarado.architecture_components.network.models.UsersResponse
import com.ivanalvarado.architecture_components.network.services.StackOverflowService
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Executor
import javax.inject.Inject

open class StackOverflowSyncer @Inject constructor(
    private val stackOverflowService: StackOverflowService,
    private val usersDao: UserDao,
    private val executor: Executor,
    private val userDetailDao: UserDetailDao
) {
    private val TAG = StackOverflowSyncer::class.java.simpleName

    @SuppressLint("CheckResult")
    fun refreshUsers() {
        stackOverflowService.getUsersRx()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({response ->
                response.userResponses.forEach { userResponse ->
                    usersDao.insert(userResponse.toUserEntity())
                }
            }, {
                Timber.tag(TAG).e(it.localizedMessage)
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