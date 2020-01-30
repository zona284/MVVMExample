package com.rakha.mvvmexample.feature.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.data.RepoData
import com.rakha.mvvmexample.databinding.ItemListRepoBinding

/**
 *   Created By rakha
 *   2020-01-29
 */
class RepoAdapter(private var repoData: MutableList<RepoData>, private var repoViewModel: RepoViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val repoItemBinding : ItemListRepoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_list_repo,parent,false)
        return RepoHolder(repoItemBinding)
    }

    override fun getItemCount(): Int = repoData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val datas = repoData[position]
        val actionListener = object : RepoItemActionListener {
            override fun onRepoClicked() {
                repoViewModel.openRepo.value = datas.html_url
            }


        }
        (holder as RepoHolder).bindRows(datas, actionListener)
    }

    fun setList(repoData: MutableList<RepoData>){
        this.repoData = repoData
        notifyDataSetChanged()
    }

    class RepoHolder(binding: ItemListRepoBinding) : RecyclerView.ViewHolder(binding.root){
        val repoItemBinding = binding

        fun bindRows(repoData: RepoData, userActionListener: RepoItemActionListener) {
            repoItemBinding.datas =  repoData
            repoItemBinding.action = userActionListener
            repoItemBinding.executePendingBindings()
        }
    }
}