package com.example.reddit_top_posts.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.reddit_top_posts.redditapi.response.PostsListResponse
import com.example.reddittopposts.R
import com.example.reddittopposts.databinding.ItemPostBinding
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Date

class PostsAdapter() :
    PagingDataAdapter<PostsListResponse.Data.Child.Post, PostsAdapter.ViewHolder>(differCallback) {

    private lateinit var binding: ItemPostBinding
    private lateinit var context: Context
    private var onItemClickListener: ((PostsListResponse.Data.Child.Post) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemPostBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    fun setOnItemClickListener(listener: (PostsListResponse.Data.Child.Post) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(item: ItemPostBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(post: PostsListResponse.Data.Child.Post) {
            binding.apply {
                author.text = "Posted by " + post.author
                title.text = post.title
                val nowDate = Date().time / 1000
                val minutesPassed = (nowDate - post.created) / 60
                if (minutesPassed < 60) {
                    created.text = "posted $minutesPassed minutes ago"
                } else {
                    val hoursPassed = minutesPassed / 60
                    created.text = "posted $hoursPassed hours ago"
                }
                thumbnail.load(post.thumbnail) {
                    placeholder(R.drawable.image_placeholder)
                }
            }
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<PostsListResponse.Data.Child.Post>() {

            override fun areItemsTheSame(
                oldItem: PostsListResponse.Data.Child.Post,
                newItem: PostsListResponse.Data.Child.Post
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PostsListResponse.Data.Child.Post,
                newItem: PostsListResponse.Data.Child.Post
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}