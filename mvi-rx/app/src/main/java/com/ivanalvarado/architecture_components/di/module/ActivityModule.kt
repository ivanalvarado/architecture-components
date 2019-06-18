package com.ivanalvarado.architecture_components.di.module

import com.ivanalvarado.architecture_components.ui.user_detail.UserDetailActivity
import com.ivanalvarado.architecture_components.ui.user_list.UserListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeUserListActivity(): UserListActivity

    @ContributesAndroidInjector()
    abstract fun contributeUserDetailActivity(): UserDetailActivity
}