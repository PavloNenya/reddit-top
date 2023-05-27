package com.example.reddit_top_posts.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.reddit_top_posts.config.POSTS_NUMBER
import com.example.reddit_top_posts.redditapi.ApiClient
import com.example.reddit_top_posts.redditapi.response.PostsListResponse

class PostSource(
    private val apiClient: ApiClient
) : PagingSource<String, PostsListResponse.Data.Child.Post>(){

    override fun getRefreshKey(state: PagingState<String, PostsListResponse.Data.Child.Post>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostsListResponse.Data.Child.Post> {
        val response = apiClient.getPosts(params.key)
        val data = response.body()!!.data
        val postsList = mutableListOf<PostsListResponse.Data.Child.Post>()
        postsList.addAll(data.posts.map { it.post })

        return LoadResult.Page(
                data = postsList,
                prevKey = data.before,
                nextKey = data.after
            )
    }


}