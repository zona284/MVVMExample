package com.rakha.mvvmexample.ui.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.utils.obtainViewModel
import kotlinx.android.synthetic.main.main_toolbar.*

/**
 *   Created By rakha
 *   22/11/20
 */
abstract class BaseActivity : AppCompatActivity() {
    lateinit var context: Context
    /**
     * Bind view to activity
     * it can be:
     * - DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
     * - setContentView(R.layout.activity_repo)
     */
    protected abstract fun initViewBinding()

    /**
     * Observe view model data
     */
    abstract fun observeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        initViewBinding()
        observeViewModel()
    }

    /**
     * get view model
     * obtainview model from factory
     */
    open fun <T: ViewModel> getViewModel(viewModel : Class<T>) : T = obtainViewModel(viewModel)

    /**
     * Enable toolbar with pagetitle
     * must include main_toolbar.xml
     */
    fun setToolbar(pageTitle: String) {
        setSupportActionBar(view_toolbar)
        tv_page_title.text = pageTitle
//        view_toolbar.setNavigationIcon(R.drawable.ic_left_arrow)
        view_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}