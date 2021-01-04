package com.rakha.mvvmexample.ui.base

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.rakha.mvvmexample.utils.inflate

/**
 *   Created By rakha
 *   29/12/20
 */
abstract class BaseViewHolder<T>(rootView: ViewDataBinding) : RecyclerView.ViewHolder(rootView.root) {
    val binding by lazy {
        rootView
    }

    abstract fun bindItem(item: T)
}