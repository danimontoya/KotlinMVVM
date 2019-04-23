package com.danieh.kotlinmvvm.core.interactor

import com.danieh.kotlinmvvm.core.exception.Failure
import com.danieh.kotlinmvvm.core.functional.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by danieh on 19/04/2019.
 *
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val job = GlobalScope.async(Dispatchers.Default) {
            run(params)
        }
        GlobalScope.launch(Dispatchers.Main) {
            onResult(job.await())
        }
    }

    class None
}