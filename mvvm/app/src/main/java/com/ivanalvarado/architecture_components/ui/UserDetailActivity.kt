package com.ivanalvarado.architecture_components.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ivanalvarado.architecture_components.R
import com.ivanalvarado.architecture_components.viewmodel.UserDetailViewModel
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import javax.inject.Inject

const val ARGUMENT_USER_ID = "ARGUMENT_USER_ID"

class UserDetailActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userDetailViewModel: UserDetailViewModel

    // UI Widgets
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var userProfileImage: ImageView
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

        setUpUi()
        fetchUserDetail()
    }

    private fun setUpUi() {
        userProfileImage = findViewById(R.id.user_detail_profile_image)
        userNameTextView = findViewById(R.id.user_detail_name_text_view)
        userReputationTextView = findViewById(R.id.user_detail_reputation_text_view)
        userLocationTextView = findViewById(R.id.user_detail_location_text_view)
        userTypeTextView = findViewById(R.id.user_detail_type_text_view)
        swipeRefreshLayout = findViewById(R.id.user_detail_swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            userDetailViewModel.refreshUserDetail(forceRefresh = true)
        }
    }

    private fun fetchUserDetail() {
        userDetailViewModel.getUserDetail().observe(this, Observer { userDetail ->
            swipeRefreshLayout.isRefreshing = false
            userDetail?.let {
                Picasso.get().load(it.imageUrl).into(userProfileImage)
                userNameTextView.text = it.userName
                userReputationTextView.text = it.reputation.toString()
                userLocationTextView.text = it.location
                userTypeTextView.text = it.userType
            }
        })
    }
}
