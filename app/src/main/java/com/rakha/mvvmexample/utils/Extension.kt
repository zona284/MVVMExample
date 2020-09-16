package com.rakha.mvvmexample.utils

import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

/**
 *   Created By rakha
 *   2020-01-22
 */

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url:String?) {
    if (url != null) {
        Glide.with(view.context).load(url).into(view)
    }
}

@BindingAdapter("visibility")
fun setVisibility(view: View, visible: Boolean) {
    if(visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("webviewContent")
fun setWebViewContent(wv: WebView, content: String) {
    wv.loadData(content!!.replace("[^\\p{ASCII}]".toRegex(), ""),
        "text/html",
        "UTF-8"
    )
}

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int){
    supportFragmentManager.beginTransaction().apply {
        replace(frameId, fragment)
    }.commit()
}

fun isValidEmail(target: CharSequence) : Boolean {
    return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches())
}