package com.ivanalvarado.architecture_components.ui.user_list

import com.ivanalvarado.architecture_components.mvibase.MviAction

sealed class UserListAction : MviAction {
    object LoadUsersAction : UserListAction()
}