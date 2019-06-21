package com.ivanalvarado.architecture_components.ui.user_list

import com.ivanalvarado.architecture_components.mvibase.MviViewState

data class UserListViewState(
    val isLoading: Boolean,
    val users: List<User>,
    val error: Throwable?
) : MviViewState {

    companion object {
        fun idle(): UserListViewState {
            return UserListViewState(
                isLoading = false,
                users = emptyList(),
                error = null
            )
        }
    }
}