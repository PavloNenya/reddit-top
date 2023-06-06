package com.example.reddit_top_posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.reddit_top_posts.config.POSTS_NUMBER
import com.example.reddit_top_posts.pagination.PostSource

class PostsViewModel : ViewModel() {

    val postsList = Pager(
        config = PagingConfig(
            pageSize = POSTS_NUMBER,
            enablePlaceholders = true,
            initialLoadSize = POSTS_NUMBER * 3
        ),
        pagingSourceFactory = { PostSource() }
    )
        .flow
        .cachedIn(viewModelScope)

}