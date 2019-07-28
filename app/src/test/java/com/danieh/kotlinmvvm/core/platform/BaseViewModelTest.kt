package com.danieh.kotlinmvvm.core.platform

import androidx.lifecycle.MutableLiveData
import com.danieh.kotlinmvvm.AndroidTest
import com.danieh.kotlinmvvm.core.exception.Failure
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.instanceOf
import org.junit.Test

/**
 * Created by danieh on 21/04/2019.
 */
class BaseViewModelTest : AndroidTest() {

    @Test
    fun `should handle failure by updating live data`() {
        val viewModel = MyViewModel()

        viewModel.handleError(Failure.NetworkConnection())

        val failure = viewModel.failure
        val error = viewModel.failure.value

        assertThat(failure, instanceOf(MutableLiveData::class.java))
        assertThat(error, instanceOf(Failure.NetworkConnection::class.java))
    }

    private class MyViewModel : BaseViewModel() {
        fun handleError(failure: Failure) = handleFailure(failure)
    }
}