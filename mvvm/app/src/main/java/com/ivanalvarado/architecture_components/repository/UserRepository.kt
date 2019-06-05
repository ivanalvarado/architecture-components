package com.ivanalvarado.architecture_components.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ivanalvarado.architecture_components.database.dao.UserDao
import com.ivanalvarado.architecture_components.database.dao.UserDetailDao
import com.ivanalvarado.architecture_components.network.StackOverflowSyncer
import com.ivanalvarado.architecture_components.repository.models.UserDetailModel
import com.ivanalvarado.architecture_components.repository.models.UserModel
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val userDetailDao: UserDetailDao,
    private val stackOverflowSyncer: StackOverflowSyncer
) {

    fun getUsers(): LiveData<List<UserModel>> {
        stackOverflowSyncer.refreshUsers()
        return Transformations.map(userDao.getUsersStream()) { users ->
            users.map {
                UserModel(
                    it.id,
                    it.userName,
                    it.reputation,
                    it.imageUrl,
                    it.websiteUrl,
                    it.lastAccessDate
                )
            }
        }
    }

    fun getUserDetail(userId: String, forceRefresh: Boolean): LiveData<UserDetailModel> {
        stackOverflowSyncer.refreshUserDetail(userId, forceRefresh)
        return Transformations.map(userDetailDao.getUserDetailStream(userId)) { userDetailEntity ->
            userDetailEntity?.let {
                UserDetailModel(
                    it.userName,
                    it.reputation,
                    it.imageUrl,
                    it.websiteUrl,
                    it.acceptRate,
                    it.location,
                    it.userType
                )
            }
        }

        TODO("Only fetch if user pulls to refresh or if data is outdated")
    }
}