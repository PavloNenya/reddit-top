package com.example.reddit_top_posts.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reddittopposts.databinding.ItemReloadBinding

class LoadPostsAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadPostsAdapter.LoadViewHolder>() {

    private lateinit var binding: ItemReloadBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadViewHolder {
        binding = ItemReloadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadViewHolder(retry)
    }

    override fun onBindViewHolder(
        holder: LoadViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    inner class LoadViewHolder(
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        init {
            binding.btnReload.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                btnReload.isVisible = loadState is LoadState.Error
            }
        }
    }


}