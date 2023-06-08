package com.example.reddit_top_posts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.reddit_top_posts.api.response.Post
import com.example.reddit_top_posts.config.POSTS_NUMBER
import com.example.reddit_top_posts.pagination.PostSource
import kotlinx.coroutines.flow.Flow

class PostsViewModel : ViewModel() {

    private val postsList = Pager(
        config = PagingConfig(
            pageSize = POSTS_NUMBER,
            enablePlaceholders = true,
            initialLoadSize = POSTS_NUMBER * 3
        ),
        pagingSourceFactory = { PostSource() }
    )
        .flow
        .cachedIn(viewModelScope)

    private val _mutableLiveData = MutableLiveData(postsList)
    val postsLiveData : LiveData<Flow<PagingData<Post>>> = _mutableLiveData
}