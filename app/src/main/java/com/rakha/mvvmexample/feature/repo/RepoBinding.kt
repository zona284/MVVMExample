package com.rakha.mvvmexample.feature.repo

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rakha.mvvmexample.data.RepoData

/**
 *   Created By rakha
 *   2020-01-29
 */

object RepoBinding {
    @BindingAdapter("app:repoList")
    @JvmStatic
    fun setRepoList(recyclerView: RecyclerView, repoData: MutableList<RepoData>) {
        with(recyclerView.adapter as RepoAdapter){
            setList(repoData)
        }
    }
}