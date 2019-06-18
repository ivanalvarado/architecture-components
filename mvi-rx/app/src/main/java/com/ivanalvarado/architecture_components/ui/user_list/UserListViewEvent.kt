package com.ivanalvarado.architecture_components.ui.user_list

sealed class UserListViewEvent {
    data class UserClick(val userId: String) : UserListViewEvent()
    object RefreshUsersSwipe : UserListViewEvent()
    data class FilterUserList(val searchTerm: String) : UserListViewEvent()
}