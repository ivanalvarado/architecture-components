package com.ivanalvarado.architecture_components.di.component

import android.app.Application
import com.ivanalvarado.architecture_components.AppController
import com.ivanalvarado.architecture_components.di.module.*
import com.ivanalvarado.architecture_components.viewmodel.UserDetailViewModel
import com.ivanalvarado.architecture_components.viewmodel.UserListViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/*
 * We mark this interface with the @Component annotation.
 * And we define all the modules that can be injected.
 * Note that we provide AndroidSupportInjectionModule.class
 * here. This class was not created by us.
 * It is an internal class in Dagger 2.10.
 * Provides our activities and fragments with given module.
 * */
@Component(
    modules = [
        ApiModule::class,
        DbModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class,
        ConcurrencyModule::class,
        AssistedInjectModule::class
    ]
)
@Singleton
interface AppComponent {

    /*
     * We will call this builder interface from our custom Application class.
     * This will set our application object to the AppComponent.
     * So inside the AppComponent the application instance is available.
     * So this application instance can be accessed by our modules
     * such as ApiModule when needed
     *
     * */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    /*
     * This is our custom Application class
     * */
    fun inject(appController: AppController)

    val userListViewModel: UserListViewModel
    val userDetailViewModelFactory: UserDetailViewModel.Factory
}

@AssistedModule
@Module(includes = [AssistedInject_AssistedInjectModule::class])
interface AssistedInjectModule