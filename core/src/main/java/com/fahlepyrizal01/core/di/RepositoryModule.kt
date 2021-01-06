package com.fahlepyrizal01.core.di

import com.fahlepyrizal01.core.data.Repository
import com.fahlepyrizal01.core.domain.repository.IRepository
import dagger.Binds
import dagger.Module

@Module(includes = [RemoteDataSourceModule::class])
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repository: Repository): IRepository
}