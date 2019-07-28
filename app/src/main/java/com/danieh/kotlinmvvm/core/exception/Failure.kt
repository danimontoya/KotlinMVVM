package com.danieh.kotlinmvvm.core.exception

/**
 * Created by danieh on 19/04/2019.
 */
sealed class Failure {

    abstract class BaseFailure : Failure()

    class NetworkConnection : BaseFailure()
    class ServerError(val throwable: Throwable?) : BaseFailure()
    class PostsUsersError : BaseFailure()
    class BodyNullError : BaseFailure()
}