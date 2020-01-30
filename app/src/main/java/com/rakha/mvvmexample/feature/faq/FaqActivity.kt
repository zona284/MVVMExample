package com.rakha.mvvmexample.feature.faq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.data.DetailsItem
import com.rakha.mvvmexample.databinding.ActivityFaqBinding
import com.rakha.mvvmexample.utils.obtainViewModel

/**
 *   Created By rakha
 *   2020-01-29
 */
class FaqActivity: AppCompatActivity() {
    lateinit var binding: ActivityFaqBinding
    lateinit var viewModel: FaqViewModel
    lateinit var faqAdapter: FaqAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        binding = DataBindingUtil.setContentView<ActivityFaqBinding>(this, R.layout.activity_faq)
        binding.apply {
            vm = viewModel
        }
        initView()
        viewModel.getFaqData()
    }

    fun setupViewModel() {
        viewModel = obtainViewModel(FaqViewModel::class.java)
    }

    fun initView(){
        viewModel?.let {
            faqAdapter = FaqAdapter(it.faqItemList, it)
            binding.rvFaq.adapter = faqAdapter
        }
    }
}