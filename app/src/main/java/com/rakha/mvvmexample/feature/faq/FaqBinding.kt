package com.rakha.mvvmexample.feature.faq

import android.widget.ViewAnimator
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rakha.mvvmexample.data.DetailsItem

/**
 *   Created By rakha
 *   2020-01-29
 */
object FaqBinding {
    @BindingAdapter("faqList")
    @JvmStatic
    fun setFaqList(recyclerView: RecyclerView, data: MutableList<DetailsItem>) {
        with(recyclerView.adapter as FaqAdapter){
            setList(data)
        }
    }

    @BindingAdapter("displayedChild")
    @JvmStatic
    fun setDisplayedChild(viewAnimator: ViewAnimator, child: Int) {
        viewAnimator.displayedChild = child
    }
}