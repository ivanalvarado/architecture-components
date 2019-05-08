package com.ivanalvarado.architecture_components.di.module

import com.ivanalvarado.architecture_components.UserListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): UserListActivity
}