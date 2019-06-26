package com.ivanalvarado.architecture_components.ui.user_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ivanalvarado.architecture_components.R
import com.ivanalvarado.architecture_components.di.viewModel
import com.ivanalvarado.architecture_components.injector
import com.ivanalvarado.architecture_components.mvibase.MviView
import com.ivanalvarado.architecture_components.repository.models.UserModel
import com.ivanalvarado.architecture_components.ui.user_detail.ARGUMENT_USER_ID
import com.ivanalvarado.architecture_components.ui.user_detail.UserDetailActivity
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.widget.RxSearchView
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class UserListActivity : AppCompatActivity(), MviView<UserListIntent, UserListViewState> {

    private val viewModel by viewModel{
        injector.userListViewModel
    }

    private lateinit var userListAdapter: UserListAdapter

    // UI Widgets
    private lateinit var usersListView: ListView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var searchView: SearchView

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpUi()
        bind()
    }

    private fun setUpUi() {
        usersListView = findViewById(R.id.user_list)
        swipeRefreshLayout = findViewById(R.id.user_list_swipe_refresh_layout)
        searchView = findViewById(R.id.user_list_search_view)

        userListAdapter = UserListAdapter(this, emptyList())
        usersListView.adapter = userListAdapter

        usersListView.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
            val user = usersListView.getItemAtPosition(position) as User

            val intent = Intent(this, UserDetailActivity::class.java).apply {
                putExtra(ARGUMENT_USER_ID, user.userId)
            }
            startActivity(intent)
        }
    }

    private fun bind() {
        // Subscribe to the ViewModel and call render for every emitted state
        disposables.add(viewModel.states().subscribe(this::render))
        // Pass the UI's intents to the ViewModel
        viewModel.processIntents(intents())
    }


    override fun intents(): Observable<UserListIntent> {
        return Observable.merge(
            initialIntent(),
            refreshIntent(),
            searchIntent()
        )
    }

    override fun render(state: UserListViewState) {
        swipeRefreshLayout.isRefreshing = state.isLoading

        if (state.users.isNotEmpty()) {
            userListAdapter.setUserList(state.users)
        }
    }

    /**
     * The initial Intent the [MviView] emit to convey to the [MviViewModel]
     * that it is ready to receive data.
     * This initial Intent is also used to pass any parameters the [MviViewModel] might need
     * to render the initial [MviViewState] (e.g. the task id to load).
     */
    private fun initialIntent(): Observable<UserListIntent.InitialIntent> {
        return Observable.just(UserListIntent.InitialIntent)
    }

    private fun refreshIntent(): Observable<UserListIntent.RefreshIntent> {
        return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
            .map { UserListIntent.RefreshIntent }
    }

    private fun searchIntent(): Observable<UserListIntent.SearchIntent> {
        return RxSearchView.queryTextChanges(searchView).map {
            UserListIntent.SearchIntent(it.toString())
        }
    }
}
