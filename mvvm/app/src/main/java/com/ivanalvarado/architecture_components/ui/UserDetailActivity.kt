package com.ivanalvarado.architecture_components.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ivanalvarado.architecture_components.R
import com.ivanalvarado.architecture_components.viewmodel.UserDetailViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

const val ARGUMENT_USER_ID = "ARGUMENT_USER_ID"

class UserDetailActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userDetailViewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val userId = intent.getStringExtra(ARGUMENT_USER_ID)

        userDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserDetailViewModel::class.java)
        userDetailViewModel.setUserId(userId)
    }

    private fun setUpUi() {

    }

    private fun fetchUserDetail() {

    }
}
