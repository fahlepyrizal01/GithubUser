package com.fahlepyrizal01.githubusers.di

import com.fahlepyrizal01.core.domain.usecase.IUseCase
import com.fahlepyrizal01.core.domain.usecase.Interactor
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    abstract fun provideUseCase(interactor: Interactor): IUseCase
}