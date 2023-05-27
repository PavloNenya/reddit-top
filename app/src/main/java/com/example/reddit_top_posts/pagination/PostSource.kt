package com.example.reddit_top_posts.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.reddit_top_posts.redditapi.ApiClient
import com.example.reddit_top_posts.redditapi.response.PostsListResponse
import kotlin.math.max

class PostSource(
    private val apiClient: ApiClient
) : PagingSource<Int, PostsListResponse.Data.Child.Post>() {
    private var lastAfter: String? = null

    override fun getRefreshKey(state: PagingState<Int, PostsListResponse.Data.Child.Post>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostsListResponse.Data.Child.Post> {
        val currentPage = params.key ?: 0
        val response = apiClient.getPosts(lastAfter)
        val data = response.body()!!.data
        val postsList = mutableListOf<PostsListResponse.Data.Child.Post>()
        postsList.addAll(data.posts.map { it.post })
        lastAfter = data.after

        Log.d("ResponsePosts", postsList.toString())
        return LoadResult.Page(
            data = postsList,
            prevKey = if (currentPage == 0) null else currentPage - 1,
            nextKey = currentPage + 1
        )

    }
}