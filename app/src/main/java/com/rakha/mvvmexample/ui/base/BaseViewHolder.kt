package com.rakha.mvvmexample.ui.base

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.rakha.mvvmexample.BR
import com.rakha.mvvmexample.utils.inflate

/**
 *   Created By rakha
 *   29/12/20
 */
abstract class BaseViewHolder<T>(rootView: ViewDataBinding) : RecyclerView.ViewHolder(rootView.root) {
    val binding by lazy {
        rootView
    }

    /**
     * This method is used for bind detail from list item into view
     * Must called this at the end of binding
     */
    open fun bindItem(item: T) {
        //BR.itemDetail is generated static constant.
        //all variable must have same name
        binding.setVariable(BR.itemDetail, item)
        binding.executePendingBindings()
    }
}