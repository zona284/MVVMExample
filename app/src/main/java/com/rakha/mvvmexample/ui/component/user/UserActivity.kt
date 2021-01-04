package com.rakha.mvvmexample.ui.component.user

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.databinding.ActivityUserBinding
import com.rakha.mvvmexample.ui.base.BaseActivity
import com.rakha.mvvmexample.ui.component.article.ArticleActivity
import com.rakha.mvvmexample.ui.component.faq.FaqActivity
import com.rakha.mvvmexample.ui.component.repo.RepoActivity

/**
 *   Created By rakha
 *   2020-01-22
 */
class UserActivity: BaseActivity() {
    val viewModel: UserViewModel by lazy {
        getViewModel(UserViewModel::class.java)
    }

    override fun initViewBinding() {
        val binding = DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
        binding.apply {
            vm = viewModel
            action = object : UserActionListener {
                override fun onClickRepos() {
                    vm?.openRepo()
                }
            }
            ivAvatar.setOnClickListener {
                startActivity(Intent(this@UserActivity, FaqActivity::class.java))
            }
            tvFollower.setOnClickListener {
                startActivity(Intent(this@UserActivity, ArticleActivity::class.java))
            }
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            userDataLiveData.observe(this@UserActivity, Observer {
                startActivity(Intent(this@UserActivity, RepoActivity::class.java))
//                Toast.makeText(this@UserActivity, "Clicked ${it.name}", Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUserData()
    }
}