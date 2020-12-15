package com.rakha.mvvmexample.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *   Created By rakha
 *   22/11/20
 */
abstract class BaseActivity : AppCompatActivity() {
    protected abstract fun initializeViewModel()
    abstract fun observeViewModel()
    protected abstract fun initViewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        initViewBinding()
        observeViewModel()
    }
}