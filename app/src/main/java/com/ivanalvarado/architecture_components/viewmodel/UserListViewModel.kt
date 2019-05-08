package com.ivanalvarado.architecture_components.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.ivanalvarado.architecture_components.repository.UserModel
import com.ivanalvarado.architecture_components.repository.UserRepository
import javax.inject.Inject

class UserListViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    init {

    }

    fun getUsers(): LiveData<List<UserModel>> = userRepository.getUsers()
}