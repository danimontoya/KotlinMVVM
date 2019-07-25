package com.danieh.kotlinmvvm

import android.app.Application
import com.danieh.kotlinmvvm.core.di.*
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by danieh on 19/04/2019.
 */
class KotlinMVVMApp : Application() {

    companion object {
        var appContext: KotlinMVVMApp? = null
        var BASE_URL = "http://jsonplaceholder.typicode.com/"
    }

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .networkModule(NetworkModule(BASE_URL))
                .dataModule(DataModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        this.injectMembers()
        this.initializeLeakDetection()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}