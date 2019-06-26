package com.ivanalvarado.architecture_components.ui.user_list

import com.ivanalvarado.architecture_components.repository.UserRepositoryImpl
import com.ivanalvarado.architecture_components.ui.user_list.UserListAction.*
import com.ivanalvarado.architecture_components.ui.user_list.UserListResult.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Contains and executes the business logic for all emitted [MviAction]
 * and returns one unique [Observable] of [MviResult].
 *
 *
 * This could have been included inside the [MviViewModel]
 * but was separated to ease maintenance, as the [MviViewModel] was getting too big.
 */
class UserListActionProcessor @Inject constructor(
    private val userRepository: UserRepositoryImpl
) {

    private val loadUsersProcessor =
        ObservableTransformer<LoadUsersAction, LoadUsersResult> { actions ->
            actions.flatMap {
                userRepository.getUsersRx()
                    // Transform the Single to an Observable to allow emission of multiple
                    // events down the stream (e.g. the InFlight event)
                    .toObservable()
                    // Wrap returned data into an immutable object
                    .map { users -> LoadUsersResult.Success(users) }
                    .cast(LoadUsersResult::class.java)
                    // without crashing.
                    // Because errors are data and hence, should just be part of the stream.
                    .onErrorReturn(LoadUsersResult::Failure)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    // Emit an InFlight event to notify the subscribers (e.g. the UI) we are
                    // doing work and waiting on a response.
                    // We emit it after observing on the UI thread to allow the event to be emitted
                    // on the current frame and avoid jank.
                    .startWith(LoadUsersResult.InFlight)
            }
        }

    private val searchUsersProcessor =
        ObservableTransformer<SearchUsersAction, SearchUsersResult> { actions ->
            actions.flatMap {
                userRepository.getUsersRx(it.searchTerm)
                    .toObservable()
                    .map { users -> SearchUsersResult(users) }
                    .cast(SearchUsersResult::class.java)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }

        }

    /**
     * Splits the [Observable] to match each type of [MviAction] to
     * its corresponding business logic processor. Each processor takes a defined [MviAction],
     * returns a defined [MviResult]
     * The global actionProcessor then merges all [Observable] back to
     * one unique [Observable].
     *
     *
     * The splitting is done using [Observable.publish] which allows almost anything
     * on the passed [Observable] as long as one and only one [Observable] is returned.
     *
     *
     * An security layer is also added for unhandled [MviAction] to allow early crash
     * at runtime to easy the maintenance.
     */
    internal var actionProcessor =
        ObservableTransformer<UserListAction, UserListResult> { actions ->
            actions.publish { userAction ->
                Observable.merge(
                    userAction.ofType(LoadUsersAction::class.java).compose(loadUsersProcessor),
                    userAction.ofType(SearchUsersAction::class.java).compose(searchUsersProcessor)
                )
            }
        }
}