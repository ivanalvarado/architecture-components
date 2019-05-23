package com.ivanalvarado.architecture_components.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.ivanalvarado.architecture_components.repository.UserModel
import com.ivanalvarado.architecture_components.repository.UserRepository
import javax.inject.Inject

class UserListViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val reloadTrigger = MutableLiveData<Boolean>()
    private val users: LiveData<List<UserModel>> = Transformations.switchMap(reloadTrigger) {
        userRepository.getUsers()
    }

    init {
        refreshUsers()
    }

    fun getUsers(): LiveData<List<UserModel>> = users

    fun refreshUsers() {
        reloadTrigger.value = true
    }
}