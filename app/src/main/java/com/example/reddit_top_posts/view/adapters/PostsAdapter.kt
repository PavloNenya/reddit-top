package com.example.reddit_top_posts.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.reddit_top_posts.api.response.PostsListResponse
import com.example.reddittopposts.R
import com.example.reddittopposts.databinding.ItemPostBinding
import java.net.URL
import java.util.concurrent.Executors


class PostsAdapter :
    PagingDataAdapter<PostsListResponse.Data.Child.Post, PostsAdapter.PostsViewHolder>(differCallback) {

    private lateinit var binding: ItemPostBinding
    private lateinit var onItemClickListener: ((PostsListResponse.Data.Child.Post) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemPostBinding.inflate(inflater, parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    fun setOnItemClickListener(listener: (PostsListResponse.Data.Child.Post) -> Unit) {
        onItemClickListener = listener
    }

    @SuppressLint("SetTextI18n")
    inner class PostsViewHolder(item: ItemPostBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(post: PostsListResponse.Data.Child.Post) {
            binding.apply {
                author.text = "Posted by " + post.author
                title.text = post.title
                created.text = post.created.toString() + " hours ago"
                comments.text = post.num_comments.toString() + " comments"
                thumbnail.load(post.thumbnail) {
                    placeholder(R.drawable.image_placeholder)
                    error(R.drawable.error_picture)
                    listener(
                        onSuccess = { imageRequest: ImageRequest, successResult: SuccessResult ->
                            progressBar.visibility = View.INVISIBLE
                            var isImage = false
                            Executors.newSingleThreadExecutor().execute {
                                try {
                                    if (URL(post.url).openConnection().contentType.contains("image/"))
                                        isImage = true
                                } catch (e: Exception) {
                                    isImage = false
                                }
                            }

                            thumbnail.setOnClickListener {
                                if (isImage) {
                                    onItemClickListener(post)
                                }
                            }
                        },
                        onError = { imageRequest: ImageRequest, successResult: ErrorResult ->
                            progressBar.visibility = View.INVISIBLE
                        }
                    )
                }
            }
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