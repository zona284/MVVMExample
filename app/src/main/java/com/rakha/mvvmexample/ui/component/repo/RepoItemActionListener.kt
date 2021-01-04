package com.rakha.mvvmexample.ui.component.repo

/**
 *   Created By rakha
 *   2020-01-22
 */
interface RepoItemActionListener {
    fun onRepoClicked()

    interface CustomClick {
        fun onItemClick()
    }
}