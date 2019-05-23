package com.ivanalvarado.template.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ivanalvarado.template.di.ViewModelFactory
import com.ivanalvarado.template.di.ViewModelKey
import com.ivanalvarado.template.viewmodel.ExampleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    /*
     * This method basically says
     * inject this object into a Map using the @IntoMap annotation,
     * with the  MovieListViewModel.class as key,
     * and a Provider that will build a MovieListViewModel
     * object.
     *
     * */
    @Binds
    @IntoMap
    @ViewModelKey(ExampleViewModel::class)
    protected abstract fun exampleListViewModel(exampleListViewModel: ExampleViewModel): ViewModel
}