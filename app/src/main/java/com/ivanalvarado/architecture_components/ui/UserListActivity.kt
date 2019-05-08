package com.ivanalvarado.architecture_components.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.ivanalvarado.architecture_components.R
import com.ivanalvarado.architecture_components.repository.UserModel
import com.ivanalvarado.architecture_components.ui.adapter.UserListAdapter
import com.ivanalvarado.architecture_components.viewmodel.UserListViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class UserListActivity : AppCompatActivity() {

    @Inject internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userListViewModel: UserListViewModel
    private lateinit var usersListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpUi()
        setUpViewModel()
    }

    private fun setUpUi() {
        usersListView = findViewById(R.id.user_list)
    }

    private fun setUpViewModel() {
        userListViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserListViewModel::class.java)
        userListViewModel.getUsers().observe(this, Observer { users -> users?.let { displayUsers(it) } })
    }

    private fun displayUsers(users: List<UserModel>) {
        val adapter = UserListAdapter(this, users)
        usersListView.adapter = adapter
    }
}
