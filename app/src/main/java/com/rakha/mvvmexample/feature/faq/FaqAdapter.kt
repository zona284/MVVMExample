package com.rakha.mvvmexample.feature.faq

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.data.DetailsItem
import com.rakha.mvvmexample.databinding.ItemListFaqBinding

/**
 *   Created By rakha
 *   2020-01-29
 */
class FaqAdapter(var data: MutableList<DetailsItem>, val viewModel: FaqViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemListFaqBinding: ItemListFaqBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_list_faq, parent, false)
        return FaqViewHolder(itemListFaqBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        (holder as FaqViewHolder).bindRow(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setList(faq: MutableList<DetailsItem>){
        this.data = faq
        notifyDataSetChanged()
    }

    class FaqViewHolder(val itemListFaqBinding: ItemListFaqBinding): RecyclerView.ViewHolder(itemListFaqBinding.root) {
        fun bindRow(data: DetailsItem) {
            itemListFaqBinding.datas = data
            itemListFaqBinding.executePendingBindings()
        }
    }
}