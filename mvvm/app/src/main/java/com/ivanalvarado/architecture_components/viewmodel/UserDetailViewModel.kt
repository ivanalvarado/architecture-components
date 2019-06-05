package com.ivanalvarado.architecture_components.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ivanalvarado.architecture_components.repository.UserRepository
import com.ivanalvarado.architecture_components.repository.models.UserDetailModel
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private lateinit var userId: String
    private val reloadTrigger = MutableLiveData<Boolean>()
    private val userDetail: LiveData<UserDetailModel> = Transformations.switchMap(reloadTrigger) {
        userRepository.getUserDetail(userId, reloadTrigger.value!!)
    }

    fun setUserId(userId: Int) {
        this.userId = userId.toString()
        refreshUserDetail()
    }

    fun getUserDetail(): LiveData<UserDetailModel> = userDetail

    fun refreshUserDetail(forceRefresh: Boolean = false) {
        reloadTrigger.value = forceRefresh
    }
}