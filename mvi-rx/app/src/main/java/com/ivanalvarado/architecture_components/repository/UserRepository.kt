package com.ivanalvarado.architecture_components.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ivanalvarado.architecture_components.database.dao.UserDao
import com.ivanalvarado.architecture_components.database.dao.UserDetailDao
import com.ivanalvarado.architecture_components.database.entity.UserDetailEntity
import com.ivanalvarado.architecture_components.database.entity.UserEntity
import com.ivanalvarado.architecture_components.network.StackOverflowSyncer
import com.ivanalvarado.architecture_components.repository.models.UserDetailModel
import com.ivanalvarado.architecture_components.repository.models.UserModel
import com.ivanalvarado.architecture_components.ui.user_list.User
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import javax.inject.Inject

interface UserRepository {
    fun getUsers(): LiveData<List<UserModel>>
    fun getUsersRx(): Observable<List<User>>
    fun getUsersRx(searchTerm: String): Observable<List<User>>
    fun getUserDetail(userId: String, forceRefresh: Boolean): LiveData<UserDetailModel>
    fun getUserDetailRx(userId: String, forceRefresh: Boolean): Observable<UserDetailModel>
}

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userDetailDao: UserDetailDao,
    private val stackOverflowSyncer: StackOverflowSyncer
) : UserRepository {

    override fun getUsers(): LiveData<List<UserModel>> {
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

    override fun getUsersRx(): Observable<List<User>> {
        stackOverflowSyncer.refreshUsers()
        return userDao.getUsersStreamRx().compose(userEntityToUser)
    }

    override fun getUsersRx(searchTerm: String): Observable<List<User>> {
        return userDao.getUsersStreamRx(searchTerm).compose(userEntityToUser)
    }

    override fun getUserDetail(userId: String, forceRefresh: Boolean): LiveData<UserDetailModel> {
        // TODO("Only fetch if user pulls to refresh or if data is outdated")
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
    }

    override fun getUserDetailRx(userId: String, forceRefresh: Boolean): Observable<UserDetailModel> {
        stackOverflowSyncer.refreshUserDetail(userId, forceRefresh)
        return userDetailDao.getUserDetailStreamRx(userId)
            .compose(userDetailEntityToUserDetail)
    }

    private val userEntityToUser = ObservableTransformer<List<UserEntity>, List<User>> { singleUserEntityList ->
        singleUserEntityList.flatMap { userEntities ->
            Observable.just(userEntities.map { User(it.id, it.userName, it.imageUrl) })
        }
    }

    private val userDetailEntityToUserDetail =
        ObservableTransformer<UserDetailEntity, UserDetailModel> { singleUserDetailEntity ->
            singleUserDetailEntity.flatMap {
                Observable.just(
                    UserDetailModel(
                        it.userName,
                        it.reputation,
                        it.imageUrl,
                        it.websiteUrl,
                        it.acceptRate,
                        it.location,
                        it.userType
                    )
                )
            }
        }
}