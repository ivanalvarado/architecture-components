package com.ivanalvarado.architecture_components.ui.user_list

import androidx.lifecycle.ViewModel
import com.ivanalvarado.architecture_components.mvibase.MviViewModel
import com.ivanalvarado.architecture_components.ui.user_list.UserListResult.LoadUsersResult
import com.ivanalvarado.architecture_components.util.notOfType
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val actionProcessorHolder: UserListActionProcessor
) : ViewModel(),
    MviViewModel<UserListIntent, UserListViewState> {

    /**
     * Proxy subject used to keep the stream alive even after the UI gets recycled.
     * This is basically used to keep ongoing events and the last cached State alive
     * while the UI disconnects and reconnects on config changes.
     */
    private val intentsSubject: PublishSubject<UserListIntent> = PublishSubject.create()
    private val statesObservable: Observable<UserListViewState> = compose()

    private val intentFilter: ObservableTransformer<UserListIntent, UserListIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge(
                    shared.ofType(UserListIntent.InitialIntent::class.java).take(1),
                    shared.notOfType(UserListIntent.InitialIntent::class.java)
                )
            }
        }

    /**
     * Compose all components to create the stream logic
     */
    private fun compose(): Observable<UserListViewState> {
        return intentsSubject
            .compose(intentFilter)
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            // Cache each state and pass it to the reducer to create a new state from
            // the previous cached one and the latest Result emitted from the action processor.
            // The Scan operator is used here for the caching.
            .scan(UserListViewState.idle(), reducer)
            // When a reducer just emits previousState, there's no reason to call render. In fact,
            // redrawing the UI in cases like this can cause jank (e.g. messing up snackbar animations
            // by showing the same snackbar twice in rapid succession).
            .distinctUntilChanged()
            // Emit the last one event of the stream on subscription
            // Useful when a View rebinds to the ViewModel after rotation.
            .replay(1)
            // Create the stream on creation without waiting for anyone to subscribe
            // This allows the stream to stay alive even when the UI disconnects and
            // match the stream's lifecycle to the ViewModel's one.
            .autoConnect(0)
    }

    /**
     * Translate an [MviIntent] to an [MviAction].
     * Used to decouple the UI and the business logic to allow easy testings and reusability.
     */
    private fun actionFromIntent(intent: UserListIntent): UserListAction {
        return when (intent) {
            UserListIntent.InitialIntent -> UserListAction.LoadUsersAction
            UserListIntent.RefreshIntent -> UserListAction.LoadUsersAction
        }
    }

    private val disposables = CompositeDisposable()

    override fun processIntents(intents: Observable<UserListIntent>) {
        disposables.add(intents.subscribe(intentsSubject::onNext))
    }

    override fun states(): Observable<UserListViewState> = statesObservable

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    companion object {
        private val reducer = BiFunction { previousState: UserListViewState, result: UserListResult ->
            when (result) {
                is LoadUsersResult -> when (result) {
                    is LoadUsersResult.Success -> {
                        previousState.copy(
                            isLoading = false,
                            users = result.users
                        )
                    }
                    is LoadUsersResult.Failure -> {
                        previousState.copy(
                            isLoading = false,
                            users = emptyList(),
                            error = result.error
                        )
                    }
                    LoadUsersResult.InFlight -> {
                        previousState.copy(isLoading = true)
                    }
                }
            }

        }
    }
}