package com.ivanalvarado.architecture_components.di.module

import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
class ConcurrencyModule {

    @Provides
    fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()

}