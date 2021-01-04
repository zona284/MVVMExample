package com.rakha.mvvmexample.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.databinding.ViewLoadMoreBinding


/**
 *   Created By rakha
 *   29/12/20
 */
open class BaseRecyclerViewAdapter<T, VH>(
    context: Context,
    layoutRes: Int,
    data: MutableList<T>?,
    recyclerAction: RecyclerAction<VH>
) : RecyclerView.Adapter<BaseViewHolder<*>?>() {

    private var context: Context
    private var data: MutableList<T>? = null
    private var inflater: LayoutInflater
    private val layoutRes: Int
    private val itemLayout = 0
    private var isShowLoadMore = false
    private var recyclerAction: RecyclerAction<VH>? = null

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_LOAD_MORE = 1
    }

    init {
        this.context = context
        setData(data)
        this.layoutRes = layoutRes
        inflater = LayoutInflater.from(context)
        this.recyclerAction = recyclerAction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_LOAD_MORE -> {
                val binding = DataBindingUtil.inflate<ViewLoadMoreBinding>(inflater, R.layout.view_load_more,null, false)
                LoadMoreViewHolder(binding!!)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater,layoutRes, parent, false)
                recyclerAction!!.getItemViewHolder(binding!!)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (holder.itemViewType == TYPE_ITEM) {
            recyclerAction!!.onCreateListItemView(holder as VH, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionFooter(position)) {
            return TYPE_LOAD_MORE
        }
        return TYPE_ITEM
    }

    /**
     * check is footer position
     *
     * @param position int
     * @return boolean
     */
    private fun isPositionFooter(position: Int): Boolean {
        return isShowLoadMore && data!![position] == null
    }

    override fun getItemCount(): Int {
        if (data == null) {
            try {
                throw Exception("data list adapter Null")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return 0
        }
        return data!!.size
    }

    fun setData(data: MutableList<T>?) {
        this.data = data
    }

    fun getData(): MutableList<T>? {
        return data
    }

    /**
     * method for returning adapter item object
     *
     * @param position int
     * @return T
     */
    fun getItem(position: Int): T? {
        return data!![position]
    }

    fun animateTo(models: List<T>) {
        applyAndAnimateRemovals(models)
        applyAndAnimateAdditions(models)
        applyAndAnimateMovedItems(models)
    }

    private fun applyAndAnimateRemovals(newModels: List<T>) {
        for (i in data!!.indices.reversed()) {
            val model = data!![i]
            if (!newModels.contains(model)) {
                removeItem(i)
            }
        }
    }

    private fun applyAndAnimateAdditions(newModels: List<T>) {
        var i = 0
        val count = newModels.size
        while (i < count) {
            val model = newModels[i]
            if (!data!!.contains(model)) {
                addItem(i, model)
            }
            i++
        }
    }

    private fun applyAndAnimateMovedItems(newModels: List<T>) {
        for (toPosition in newModels.indices.reversed()) {
            val model = newModels[toPosition]
            val fromPosition = data!!.indexOf(model)
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition)
            }
        }
    }

    fun removeItem(position: Int): T? {
        val model: T? = data?.removeAt(position)
        notifyItemRemoved(position)
        return model
    }

    fun addItem(position: Int, model: T) {
        data?.add(position, model)
        notifyItemInserted(position)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val model: T? = data?.removeAt(fromPosition)
        model?.let { data?.add(toPosition, it) }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun setShowLoadMore(showLoadMore: Boolean) {
        isShowLoadMore = showLoadMore
    }

    /**
     * Define Action
     */
    interface RecyclerAction<VH> {
        fun getItemViewHolder(binding: ViewDataBinding): BaseViewHolder<*>
        fun onCreateListItemView(holder: VH, position: Int)
    }
}