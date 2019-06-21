package com.ivanalvarado.architecture_components.ui.user_list

import com.ivanalvarado.architecture_components.repository.UserRepository
import com.ivanalvarado.architecture_components.ui.user_list.UserListAction.LoadUsersAction
import com.ivanalvarado.architecture_components.util.BaseSchedulerProvider
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class UserListActionProcessor @Inject constructor(
    private val userRepository: UserRepository,
    private val schedulerProvider: BaseSchedulerProvider
) {

//    private val loadUsersProcessor =
//        ObservableTransformer<LoadUsersAction, UserListResult> { actions ->
//            actions.flatMap { action ->
//                userRepository.getUsersRx()
//                    .toObservable()
//            }
//        }
}