package com.ivanalvarado.architecture_components.ui.user_list

import com.ivanalvarado.architecture_components.mvibase.MviIntent

sealed class UserListIntent : MviIntent {
    object InitialIntent : UserListIntent()

    object RefreshIntent : UserListIntent()

    data class SearchIntent(val searchTerm: String) : UserListIntent()
}