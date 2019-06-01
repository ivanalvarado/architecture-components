package com.ivanalvarado.architecture_components.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ivanalvarado.architecture_components.R
import com.ivanalvarado.architecture_components.viewmodel.UserDetailViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

const val ARGUMENT_USER_ID = "ARGUMENT_USER_ID"

class UserDetailActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userDetailViewModel: UserDetailViewModel

    // UI Widgets
    private lateinit var userNameTextView: TextView
    private lateinit var userReputationTextView: TextView
    private lateinit var userLocationTextView: TextView
    private lateinit var userTypeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val userId = intent.getIntExtra(ARGUMENT_USER_ID, 0)

        userDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserDetailViewModel::class.java)
        userDetailViewModel.setUserId(userId)
        fetchUserDetail()
    }

    private fun setUpUi() {
        userNameTextView = findViewById(R.id.user_detail_name_text_view)
        userReputationTextView = findViewById(R.id.user_detail_reputation_text_view)
        userLocationTextView = findViewById(R.id.user_detail_location_text_view)
        userTypeTextView = findViewById(R.id.user_detail_type_text_view)
    }

    private fun fetchUserDetail() {
        userDetailViewModel.getUserDetail().observe(this, Observer { TODO("Display UserDetail") })
    }
}
