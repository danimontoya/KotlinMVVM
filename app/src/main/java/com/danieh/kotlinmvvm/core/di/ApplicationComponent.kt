package com.danieh.kotlinmvvm.core.di

import com.danieh.kotlinmvvm.KotlinMVVMApp
import com.danieh.kotlinmvvm.core.di.viewmodel.ViewModelModule
import com.danieh.kotlinmvvm.features.presentation.MainActivity
import com.danieh.kotlinmvvm.features.presentation.postdetails.PostDetailsFragment
import com.danieh.kotlinmvvm.features.presentation.posts.PostsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by danieh on 19/04/2019.
 */
@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(application: KotlinMVVMApp)

    fun inject(mainActivity: MainActivity)

    fun inject(postsFragment: PostsFragment)

    fun inject(postDetailsFragment: PostDetailsFragment)
}
