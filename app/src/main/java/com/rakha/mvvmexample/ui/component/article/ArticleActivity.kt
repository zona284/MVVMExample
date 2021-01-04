package com.rakha.mvvmexample.ui.component.article

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
                articleViewModel.refreshFetchActicle()
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
        articleViewModel.refreshFetchActicle()
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
                        holder.itemView.setOnClickListener {
                            Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        )

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.isSmoothScrollbarEnabled = false
        viewBinding.rvArticle.apply {
            layoutManager = linearLayoutManager
            adapter = articleAdapter
            loadMoreListener = object : EndlessRecyclerOnScrollListener(linearLayoutManager) {
                override fun onLoadMore(offset: Int) {
                    Log.d(TAG, "onLoadMore: ${articleViewModel.isFinishLoadApi.get()} ${articleViewModel.isNextPageAvailable.get()}")
                    if (articleViewModel.isFinishLoadApi.get() && articleViewModel.isNextPageAvailable.get()) {
                        articleViewModel.isFinishLoadApi.set(false)
                        articleViewModel.currentPage.set(articleViewModel.currentPage.get()+1)
                        post {
                            //add null data to show loadmore
                            articleAdapter.setShowLoadMore(true)
                            articleViewModel.articles.add(null)
                            articleAdapter.notifyItemInserted(articleViewModel.articles.size - 1)
                            //fetch data
                            articleViewModel.getArticleData(isLoadMore = true, isRefresh = false)
                        }
                    }
                }
            }
            addOnScrollListener(loadMoreListener)
        }
    }
}