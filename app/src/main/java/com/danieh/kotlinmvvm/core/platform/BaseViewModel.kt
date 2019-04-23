package com.danieh.kotlinmvvm.core.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danieh.kotlinmvvm.core.exception.Failure

/**
 * Created by danieh on 19/04/2019.
 *
 * Base ViewModel class with default Failure handling.
 */
abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure?) {
        this.failure.value = failure
    }
}