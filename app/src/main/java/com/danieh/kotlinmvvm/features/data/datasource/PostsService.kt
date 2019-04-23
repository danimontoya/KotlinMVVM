package com.danieh.kotlinmvvm.features.data.datasource

import com.danieh.kotlinmvvm.features.data.model.CommentEntity
import com.danieh.kotlinmvvm.features.data.model.PostEntity
import com.danieh.kotlinmvvm.features.data.model.UserEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by danieh on 19/04/2019.
 */
@Singleton
class PostsService
@Inject constructor(retrofit: Retrofit) : Api {

    private val api by lazy { retrofit.create(Api::class.java) }

    override fun posts(): Call<List<PostEntity>> = api.posts()

    override fun comments(): Call<List<CommentEntity>> = api.comments()

    override fun users(): Call<List<UserEntity>> = api.users()
}
