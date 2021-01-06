package com.fahlepyrizal01.githubusers.di

import com.fahlepyrizal01.core.di.CoreComponent
import com.fahlepyrizal01.githubusers.presenter.fragment.SearchFragment
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(fragment: SearchFragment)
}