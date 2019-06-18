package com.ivanalvarado.architecture_components.ui.user_list

sealed class SyncState {
    object Idle: SyncState()

    data class Process(val type: Type): SyncState() {
        enum class Type { REFRESH }
    }

    data class Error(val error: Throwable): SyncState()
}

data class UserListState(
    val users: List<User>,
    val syncState: SyncState
)