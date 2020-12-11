package com.rakha.mvvmexample.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 *   Created By rakha
 *   17/09/20
 */
open class TestObserver<T> : Observer<T> {

    val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>()
    .also { observeForever(it) }