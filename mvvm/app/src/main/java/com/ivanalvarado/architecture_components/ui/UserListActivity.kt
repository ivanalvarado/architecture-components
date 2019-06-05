package com.ivanalvarado.architecture_components.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ivanalvarado.architecture_components.R
import com.ivanalvarado.architecture_components.repository.models.UserModel
import com.ivanalvarado.architecture_components.ui.adapter.UserListAdapter
import com.ivanalvarado.architecture_components.viewmodel.UserListViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class UserListActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userListViewModel: UserListViewModel
    private lateinit var userListAdapter: UserListAdapter

    // UI Widgets
    private lateinit var usersListView: ListView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userListViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserListViewModel::class.java)

        setUpUi()
        fetchUsers()
    }

    private fun setUpUi() {
        userListAdapter = UserListAdapter(this, emptyList())
        usersListView = findViewById(R.id.user_list)
        usersListView.adapter = userListAdapter
        swipeRefreshLayout = findViewById(R.id.user_list_swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            userListViewModel.refreshUsers()
        }

        usersListView.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
            val user = usersListView.getItemAtPosition(position) as UserModel
            val intent = Intent(this, UserDetailActivity::class.java).apply {
                putExtra(ARGUMENT_USER_ID, user.userId)
            }
            startActivity(intent)
        }
    }

    private fun fetchUsers() {
        swipeRefreshLayout.isRefreshing = true
        userListViewModel.getUsers().observe(this, Observer { users -> users?.let { displayUsers(it) } })
    }

    private fun displayUsers(users: List<UserModel>) {
        swipeRefreshLayout.isRefreshing = false
        userListAdapter.setUserList(users)
    }
}
