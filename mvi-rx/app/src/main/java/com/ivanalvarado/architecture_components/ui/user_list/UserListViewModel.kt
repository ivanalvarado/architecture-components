package com.ivanalvarado.architecture_components.ui.user_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ivanalvarado.architecture_components.repository.models.UserModel
import com.ivanalvarado.architecture_components.repository.UserRepository
import com.ivanalvarado.architecture_components.ui.user_list.UserListViewEvent.*
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

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

    fun onUiEvent(viewEvent: UserListViewEvent) {
        when (viewEvent) {
            is UserClick -> TODO()
            RefreshUsersSwipe -> TODO()
            is FilterUserList -> TODO()
        }
    }

    private fun processUserClickIntent(viewEvent: UserClick) {

    }
}