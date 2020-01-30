package com.rakha.mvvmexample.feature.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.databinding.ActivityUserBinding
import com.rakha.mvvmexample.feature.faq.FaqActivity
import com.rakha.mvvmexample.feature.repo.RepoActivity
import com.rakha.mvvmexample.utils.obtainViewModel
import kotlinx.android.synthetic.main.activity_user.*

/**
 *   Created By rakha
 *   2020-01-22
 */
class UserActivity: AppCompatActivity() {
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        val binding = DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
        binding.apply {
            vm = viewModel
            action = object : UserActionListener {
                override fun onClickRepos() {
                    vm?.openRepo()
                }
            }
        }
        initView()
        viewModel.getUserData()
    }

    fun initView() {
        ivAvatar.setOnClickListener {
            startActivity(Intent(this@UserActivity, FaqActivity::class.java))
        }
    }

    fun setupViewModel() {
        viewModel = getUserViewModel().apply {
            userDataLiveData.observe(this@UserActivity, Observer {
                startActivity(Intent(this@UserActivity, RepoActivity::class.java))
//                Toast.makeText(this@UserActivity, "Clicked ${it.name}", Toast.LENGTH_SHORT).show()
            })
        }
    }

    fun getUserViewModel(): UserViewModel = obtainViewModel(UserViewModel::class.java)
}