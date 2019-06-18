package com.ivanalvarado.architecture_components.ui.user_list

import com.ivanalvarado.architecture_components.model.ModelStore
import javax.inject.Inject

class UserListModelStore @Inject constructor() :
        ModelStore<UserListState>(
            UserListState.Idle(
                emptyList(),
                SyncState.Idle
            )
        )