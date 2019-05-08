package com.ivanalvarado.architecture_components

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ivanalvarado.architecture_components.repository.UserModel
import com.ivanalvarado.architecture_components.viewmodel.UserListViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class UserListActivity : AppCompatActivity() {

    @Inject internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userListViewModel: UserListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()
    }

    private fun setUpViewModel() {
        userListViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserListViewModel::class.java)
        userListViewModel.getUsers().observe(this, Observer { users -> users?.let { displayUsers(it) } })
    }

    private fun displayUsers(users: List<UserModel>) {
        TODO("Not implemented, but do whatever with Users list on UI thread.")
    }
}
