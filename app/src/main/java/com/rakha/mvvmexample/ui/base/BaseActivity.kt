package com.rakha.mvvmexample.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *   Created By rakha
 *   22/11/20
 */
abstract class BaseActivity : AppCompatActivity() {
    /**
     * init view model - obtainviewmodel function
     */
    protected abstract fun initializeViewModel()

    /**
     * Observe view model data
     */
    abstract fun observeViewModel()

    /**
     * Bind view to activity
     * it can be:
     * - setContentView(R.layout.activity_repo)
     * - DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
     */
    protected abstract fun initViewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        initViewBinding()
        observeViewModel()
    }
}