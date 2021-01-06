package com.fahlepyrizal01.core.di

import android.content.Context
import com.fahlepyrizal01.core.data.source.remote.IRemoteDataSource
import com.fahlepyrizal01.core.domain.repository.IRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface CoreComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideRemoteDataSource(): IRemoteDataSource
    fun provideRepository(): IRepository
}