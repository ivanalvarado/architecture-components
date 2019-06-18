package com.ivanalvarado.architecture_components.ui.user_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ivanalvarado.architecture_components.model.Intent
import com.ivanalvarado.architecture_components.model.intent
import com.ivanalvarado.architecture_components.repository.models.UserModel
import com.ivanalvarado.architecture_components.repository.UserRepository
import com.ivanalvarado.architecture_components.ui.user_list.UserListViewEvent.*
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userListModelStore: UserListModelStore
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
        userListModelStore.process(toIntent(viewEvent))
    }

    private fun toIntent(viewEvent: UserListViewEvent): Intent<UserListState> {
        return when (viewEvent) {
            is UserClick -> buildUserClickIntent(viewEvent)
            RefreshUsersSwipe -> TODO()
            is FilterUserList -> TODO()
        }
    }

    private fun buildUserClickIntent(viewEvent: UserClick) = userListIntent<UserListState.Idle> {
        userClicked(viewEvent.userId)
    }


}

/**
 * Creates an intent for the TaskEditor state machine.
 *
 * Utility function to cut down on boilerplate.
 */
inline fun <reified S : UserListState> userListIntent(
    crossinline block: S.() -> UserListState
): Intent<UserListState> {
    return intent {
        (this as? S)?.block()
            ?: throw IllegalStateException("userListIntent encountered an inconsistent state.")
    }
}