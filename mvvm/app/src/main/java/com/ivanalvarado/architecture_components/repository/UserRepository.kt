package com.ivanalvarado.architecture_components.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.ivanalvarado.architecture_components.database.dao.UserDao
import com.ivanalvarado.architecture_components.network.StackOverflowSyncer
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val stackOverflowSyncer: StackOverflowSyncer
) {

    fun getUsers(): LiveData<List<UserModel>> {
        stackOverflowSyncer.refreshUsers()
        return Transformations.map(userDao.getUsersStream()) { users ->
            users.map { UserModel(it.userName, it.reputation, it.imageUrl, it.websiteUrl, it.lastAccessDate) }
        }
    }
}