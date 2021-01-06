package com.fahlepyrizal01.core.di

import com.fahlepyrizal01.core.data.source.remote.IRemoteDataSource
import com.fahlepyrizal01.core.data.source.remote.RemoteDataSource
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource
}