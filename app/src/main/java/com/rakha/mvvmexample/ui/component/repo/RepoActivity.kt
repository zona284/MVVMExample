package com.rakha.mvvmexample.ui.component.repo

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.ui.base.BaseActivity
import com.rakha.mvvmexample.utils.obtainViewModel
import com.rakha.mvvmexample.utils.replaceFragmentInActivity

/**
 *   Created By rakha
 *   2020-01-22
 */
class RepoActivity : BaseActivity() {
    private lateinit var mActivity: AppCompatActivity
    private lateinit var viewModel: RepoViewModel

    override fun initializeViewModel() {
        viewModel = getViewModel(RepoViewModel::class.java)
    }

    override fun observeViewModel() {
        viewModel.apply {
            openRepo.observe(this@RepoActivity, Observer{
                onRepoClicked(it!!)
            })
        }
    }

    override fun initViewBinding() {
        setContentView(R.layout.activity_repo)
        setupFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
    }

    private fun setupFragment() {
        supportFragmentManager.findFragmentById(R.id.frameRepo)
        RepoFragment.newInstance().let {
            replaceFragmentInActivity(it, R.id.frameRepo)
        }
    }

    fun onRepoClicked(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        builder.setToolbarColor(ContextCompat.getColor(mActivity, R.color.colorPrimary))
        customTabsIntent.launchUrl(mActivity, Uri.parse(url))
    }

}
