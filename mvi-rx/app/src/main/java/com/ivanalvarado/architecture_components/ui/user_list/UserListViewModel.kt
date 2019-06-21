package com.ivanalvarado.architecture_components.ui.user_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ivanalvarado.architecture_components.model.Intent
import com.ivanalvarado.architecture_components.model.intent
import com.ivanalvarado.architecture_components.mvibase.MviViewModel
import com.ivanalvarado.architecture_components.repository.UserRepository
import com.ivanalvarado.architecture_components.repository.UserRepositoryImpl
import com.ivanalvarado.architecture_components.repository.models.UserModel
import com.ivanalvarado.architecture_components.ui.user_list.UserListViewEvent.*
import com.ivanalvarado.architecture_components.util.notOfType
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val userListModelStore: UserListModelStore
) : ViewModel(),
    MviViewModel<UserListIntent, UserListViewState> {

    /**
     * Proxy subject used to keep the stream alive even after the UI gets recycled.
     * This is basically used to keep ongoing events and the last cached State alive
     * while the UI disconnects and reconnects on config changes.
     */
    private val intentsSubject: PublishSubject<UserListIntent> = PublishSubject.create()
//    private val statesObservable: Observable<UserListViewState> = compose()

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
//    private fun compose(): Observable<UserListViewState> {
//        return intentsSubject
//            .compose(intentFilter)
//            .map(this::actionFromIntent)
//            .compose(actionProcessorHolder.actionProcessor)
//            // Cache each state and pass it to the reducer to create a new state from
//            // the previous cached one and the latest Result emitted from the action processor.
//            // The Scan operator is used here for the caching.
//            .scan(TasksViewState.idle(), reducer)
//            // When a reducer just emits previousState, there's no reason to call render. In fact,
//            // redrawing the UI in cases like this can cause jank (e.g. messing up snackbar animations
//            // by showing the same snackbar twice in rapid succession).
//            .distinctUntilChanged()
//            // Emit the last one event of the stream on subscription
//            // Useful when a View rebinds to the ViewModel after rotation.
//            .replay(1)
//            // Create the stream on creation without waiting for anyone to subscribe
//            // This allows the stream to stay alive even when the UI disconnects and
//            // match the stream's lifecycle to the ViewModel's one.
//            .autoConnect(0)
//    }

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

    private val reloadTrigger = MutableLiveData<Boolean>()
    private val users: LiveData<List<UserModel>> = Transformations.switchMap(reloadTrigger) {
        userRepository.getUsers()
    }

    private val disposables = CompositeDisposable()

    init {
        refreshUsers()
    }

    fun getUsers(): LiveData<List<UserModel>> = users

    fun refreshUsers() {
        reloadTrigger.value = true
    }

    fun onUiEvent(viewEvent: UserListViewEvent) {
        userListModelStore.process(toIntent(viewEvent))
    }

    override fun processIntents(intents: Observable<UserListIntent>) {
        disposables.add(intents.subscribe(intentsSubject::onNext))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun states(): Observable<UserListViewState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun toIntent(viewEvent: UserListViewEvent): Intent<UserListState> {
        return when (viewEvent) {
            is UserClick -> buildUserClickIntent(viewEvent)
            RefreshUsersSwipe -> TODO()
            is FilterUserList -> TODO()
        }
    }

    private fun buildUserClickIntent(viewEvent: UserClick) = userListIntent<UserListState.Idle> {
        userClicked(viewEvent.userId)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}

/**
 * Creates an intent for the UserList state machine.
 *
 * Utility function to cut down on boilerplate.
 */
inline fun <reified S : UserListState> userListIntent(
    crossinline block: S.() -> UserListState
): Intent<UserListState> {
    return intent {
        (this as? S)?.block()
            ?: throw IllegalStateException("userListIntent encountered an inconsistent state.")
    }
}