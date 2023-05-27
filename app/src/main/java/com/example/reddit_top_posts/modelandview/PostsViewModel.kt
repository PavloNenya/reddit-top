package com.example.reddit_top_posts.modelandview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.reddit_top_posts.config.POSTS_NUMBER
import com.example.reddit_top_posts.pagination.PostSource
import com.example.reddit_top_posts.redditapi.ApiClient

class PostsViewModel() : ViewModel() {
    private val apiClient = ApiClient()

    val postsList = Pager(
        config = PagingConfig(
            pageSize = POSTS_NUMBER,
            enablePlaceholders = true,
            initialLoadSize = POSTS_NUMBER * 3
        ),
        pagingSourceFactory = { PostSource(apiClient) }
    )
        .flow
        .cachedIn(viewModelScope)
}