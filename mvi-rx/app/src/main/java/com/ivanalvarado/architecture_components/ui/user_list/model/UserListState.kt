package com.ivanalvarado.architecture_components.ui.user_list.model

sealed class UserListSyncState {

    object Idle : UserListSyncState()

    object Error : UserListSyncState()
}