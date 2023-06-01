package com.example.reddit_top_posts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reddittopposts.databinding.ItemReloadBinding

class LoadPostsAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadPostsAdapter.ViewHolder>() {

    private lateinit var binding: ItemReloadBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder {
        binding = ItemReloadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(retry)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    inner class ViewHolder(
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
                btnReload.isVisible = loadState is LoadState.Error
            }
        }
    }


}