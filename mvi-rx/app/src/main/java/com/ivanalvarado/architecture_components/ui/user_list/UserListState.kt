package com.ivanalvarado.architecture_components.ui.user_list

import com.ivanalvarado.architecture_components.ui.user_list.SyncState.*

sealed class SyncState {
    object Idle: SyncState()

    data class Process(val type: Type): SyncState() {
        enum class Type { REFRESH }
    }

    data class Error(val error: Throwable): SyncState()
}

sealed class UserListState {
    data class Idle(val users: List<User>, val syncState: SyncState) : UserListState() {
        fun userClicked(userId: Int) = SelectedUser(userId)
    }

    data class SelectedUser(val userId: Int) : UserListState()

}
