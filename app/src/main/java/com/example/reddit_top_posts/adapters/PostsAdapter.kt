package com.example.reddit_top_posts.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.reddit_top_posts.redditapi.response.PostsListResponse
import com.example.reddittopposts.R
import com.example.reddittopposts.databinding.ItemPostBinding
import java.util.Date

class PostsAdapter :
    PagingDataAdapter<PostsListResponse.Data.Child.Post, PostsAdapter.PostsViewHolder>(differCallback) {

    private lateinit var binding: ItemPostBinding
    private lateinit var context: Context
    private var onItemClickListener: ((PostsListResponse.Data.Child.Post) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemPostBinding.inflate(inflater, parent, false)
        context = parent.context
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        Log.d("Position", position.toString())
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    fun setOnItemClickListener(listener: (PostsListResponse.Data.Child.Post) -> Unit) {
        onItemClickListener = listener
    }

    @SuppressLint("SetTextI18n")
    inner class PostsViewHolder(item: ItemPostBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(post: PostsListResponse.Data.Child.Post) {
            Log.d("Post", post.toString())
            binding.apply {
                author.text = "Posted by " + post.author
                title.text = post.title
                created.text = calculateTime(post.created)
                thumbnail.load(post.thumbnail) {
                    placeholder(R.drawable.image_placeholder)
                }
            }
        }
    }

    private fun calculateTime(created: Long) : String {
        val nowDate = Date().time / 1000
        val minutesPassed = (nowDate - created) / 60
        return if (minutesPassed < 60) {
            "posted $minutesPassed minutes ago"
        } else {
            val hoursPassed = minutesPassed / 60
            "posted $hoursPassed hours ago"
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<PostsListResponse.Data.Child.Post>() {
            override fun areItemsTheSame(
                oldItem: PostsListResponse.Data.Child.Post,
                newItem: PostsListResponse.Data.Child.Post
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PostsListResponse.Data.Child.Post,
                newItem: PostsListResponse.Data.Child.Post
            ) = oldItem == newItem

        }
    }
}