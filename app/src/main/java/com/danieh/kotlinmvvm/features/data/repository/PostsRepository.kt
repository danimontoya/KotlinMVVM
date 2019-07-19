package com.danieh.kotlinmvvm.features.data.repository

import arrow.core.Either
import com.danieh.kotlinmvvm.core.exception.Failure
import com.danieh.kotlinmvvm.core.platform.NetworkHandler
import com.danieh.kotlinmvvm.features.data.datasource.PostsService
import com.danieh.kotlinmvvm.features.domain.model.Comment
import com.danieh.kotlinmvvm.features.domain.model.Post
import com.danieh.kotlinmvvm.features.domain.model.User
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by danieh on 19/04/2019.
 */
interface PostsRepository {

    fun posts(): Either<Failure, List<Post>>
    fun comments(): Either<Failure, List<Comment>>
    fun users(): Either<Failure, List<User>>

    class Network @Inject constructor(private val networkHandler: NetworkHandler, private val service: PostsService) :
        PostsRepository {

        override fun posts(): Either<Failure, List<Post>> {
            return when (networkHandler.isConnected) {
                true -> request(service.posts()) { postEntities -> postEntities.map { it.toPost() } }
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

        override fun comments(): Either<Failure, List<Comment>> {
            return when (networkHandler.isConnected) {
                true -> request(service.comments()) { commentEntities -> commentEntities.map { it.toComment() } }
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

        override fun users(): Either<Failure, List<User>> {
            return when (networkHandler.isConnected) {
                true -> request(service.users()) { userEntities -> userEntities.map { it.toUser() } }
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> {
                        response.body()?.let { Either.Right(transform(it)) }
                            ?: Either.Left(Failure.BodyNullError())
                    }
                    false -> Either.Left(Failure.ServerError(Throwable(response.errorBody()?.string())))
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError(exception))
            }
        }
    }
}
