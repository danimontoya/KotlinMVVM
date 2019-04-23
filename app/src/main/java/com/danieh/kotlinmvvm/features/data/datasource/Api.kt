package com.danieh.kotlinmvvm.features.data.datasource

import com.danieh.kotlinmvvm.features.data.model.CommentEntity
import com.danieh.kotlinmvvm.features.data.model.PostEntity
import com.danieh.kotlinmvvm.features.data.model.UserEntity
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by danieh on 19/04/2019.
 */
interface Api {

    @GET("posts")
    fun posts(): Call<List<PostEntity>>

    @GET("comments")
    fun comments(): Call<List<CommentEntity>>

    @GET("users")
    fun users(): Call<List<UserEntity>>
}
