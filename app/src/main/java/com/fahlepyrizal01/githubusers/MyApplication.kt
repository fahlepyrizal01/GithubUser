package com.fahlepyrizal01.githubusers

import android.app.Application
import com.fahlepyrizal01.core.di.CoreComponent
import com.fahlepyrizal01.core.di.DaggerCoreComponent
import com.fahlepyrizal01.githubusers.di.AppComponent
import com.fahlepyrizal01.githubusers.di.DaggerAppComponent

open class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}