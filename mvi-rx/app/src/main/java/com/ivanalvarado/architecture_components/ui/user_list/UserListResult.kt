package com.ivanalvarado.architecture_components.ui.user_list

import com.ivanalvarado.architecture_components.mvibase.MviResult

sealed class UserListResult : MviResult {
    sealed class LoadUsersResult : UserListResult() {
        data class Success(val users: List<User>) : LoadUsersResult()
        data class Failure(val error: Throwable) : LoadUsersResult()
        object InFlight : LoadUsersResult()
    }
}