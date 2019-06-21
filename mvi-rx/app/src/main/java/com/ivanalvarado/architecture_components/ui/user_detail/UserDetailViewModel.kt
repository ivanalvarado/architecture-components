package com.ivanalvarado.architecture_components.ui.user_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ivanalvarado.architecture_components.repository.UserRepository
import com.ivanalvarado.architecture_components.repository.UserRepositoryImpl
import com.ivanalvarado.architecture_components.repository.models.UserDetailModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class UserDetailViewModel @AssistedInject constructor(
    private val userRepository: UserRepositoryImpl,
    @Assisted private val userId: Int
) : ViewModel() {

    private val reloadTrigger = MutableLiveData<Boolean>()
    private val userDetail: LiveData<UserDetailModel> = Transformations.switchMap(reloadTrigger) {
        userRepository.getUserDetail(userId.toString(), reloadTrigger.value!!)
    }

    init {
        refreshUserDetail()
    }

    fun getUserDetail(): LiveData<UserDetailModel> = userDetail

    fun refreshUserDetail(forceRefresh: Boolean = false) {
        reloadTrigger.value = forceRefresh
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(userId: Int): UserDetailViewModel
    }
}