package com.ivanalvarado.architecture_components.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.ivanalvarado.architecture_components.repository.UserRepository
import com.ivanalvarado.architecture_components.repository.models.UserDetailModel
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private lateinit var userId: String
    private val reloadTrigger = MutableLiveData<Boolean>()
    private val userDetail: LiveData<UserDetailModel> = Transformations.switchMap(reloadTrigger) {
        userRepository.getUserDetail(userId)
    }

    fun setUserId(userId: Int) {
        this.userId = userId.toString()
        refreshUserDetail()
    }

    fun getUserDetail(): LiveData<UserDetailModel> = userDetail

    fun refreshUserDetail() {
        reloadTrigger.value = true
    }
}