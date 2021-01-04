package com.rakha.mvvmexample.ui.component.article

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.data.ArticleData
import com.rakha.mvvmexample.databinding.ActivityArticleBinding
import com.rakha.mvvmexample.databinding.ItemListArticleBinding
import com.rakha.mvvmexample.ui.base.BaseActivity
import com.rakha.mvvmexample.ui.base.BaseRecyclerViewAdapter
import com.rakha.mvvmexample.ui.base.BaseViewHolder
import com.rakha.mvvmexample.ui.viewholder.ArticleViewHolder
import com.rakha.mvvmexample.utils.EndlessRecyclerOnScrollListener

/**
 *   Created By rakha
 *   03/01/21
 */
class ArticleActivity: BaseActivity() {

    lateinit var viewBinding: ActivityArticleBinding
    lateinit var articleAdapter: BaseRecyclerViewAdapter<ArticleData, ArticleViewHolder>
    lateinit var loadMoreListener: EndlessRecyclerOnScrollListener

    val articleViewModel by lazy {
        getViewModel(ArticleViewModel::class.java)
    }

    override fun initViewBinding() {
        viewBinding = DataBindingUtil.setContentView<ActivityArticleBinding>(this@ArticleActivity, R.layout.activity_article)
        viewBinding.apply {
            viewModel = articleViewModel
            lifecycleOwner = this@ArticleActivity
            swipeRefresh.setOnRefreshListener {
                loadMoreListener.reset()
                articleViewModel.currentPage.set(1)
                articleViewModel.isFinishLoadApi.set(false)
                articleViewModel.isNextPageAvailable.set(true)
                articleViewModel.getArticleData( false, true)
            }
        }

    }

    override fun observeViewModel() {
        articleViewModel.apply {
            isLoadedMore.observe(this@ArticleActivity, Observer {
                if(it) {
                    articleAdapter.setShowLoadMore(false)
                    articles.removeAt(articles.size - 1)
                    articleAdapter.notifyItemRemoved(articles.size - 1)
                }
            })
            isRefreshed.observe(this@ArticleActivity, Observer {
                if(it) {
                    articles.clear()
                    viewBinding.swipeRefresh.isRefreshing = false
                }
            })

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar((getString(R.string.app_name)))
        initRecyclerView()
        articleViewModel.getArticleData( false, true)
    }

    fun initRecyclerView() {
        articleAdapter = BaseRecyclerViewAdapter<ArticleData, ArticleViewHolder> (
            context,
            R.layout.item_list_article,
            articleViewModel.articles,
            object : BaseRecyclerViewAdapter.RecyclerAction<ArticleViewHolder> {
                override fun getItemViewHolder(binding: ViewDataBinding): BaseViewHolder<*> {
                    return ArticleViewHolder(binding as ItemListArticleBinding)
                }

                override fun onCreateListItemView(holder: ArticleViewHolder, position: Int) {
                    val item = articleAdapter.getItem(position)
                    item?.let {
                        holder.bindItem(item)
                    }
                }
            }
        )

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.isSmoothScrollbarEnabled = true
        loadMoreListener = object : EndlessRecyclerOnScrollListener(linearLayoutManager) {
            override fun onLoadMore(offset: Int) {
                Log.d(TAG, "onLoadMore: ${articleViewModel.isFinishLoadApi.get()} ${articleViewModel.isNextPageAvailable.get()}")
                if (articleViewModel.isFinishLoadApi.get() && articleViewModel.isNextPageAvailable.get()) {
                    //add null data to show loadmore
                    articleAdapter.setShowLoadMore(true)
                    articleViewModel.articles.add(null)
                    articleAdapter.notifyItemInserted(articleViewModel.articles.size - 1)
                    //fetch data
                    articleViewModel.isFinishLoadApi.set(false)
                    articleViewModel.currentPage.set(articleViewModel.currentPage.get()+1)
                    articleViewModel.getArticleData(isLoadMore = true, isRefresh = false)
                }
            }
        }
        viewBinding.rvArticle.apply {
            layoutManager = linearLayoutManager
            adapter = articleAdapter
            addOnScrollListener(loadMoreListener)
        }
    }
}