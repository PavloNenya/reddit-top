package com.example.reddit_top_posts.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.reddit_top_posts.api.ApiClient
import com.example.reddit_top_posts.api.response.PostsListResponse
import retrofit2.HttpException
import java.util.*

class PostSource : PagingSource<Int, PostsListResponse.Data.Child.Post>() {
    private var lastAfter: String? = null

    override fun getRefreshKey(state: PagingState<Int, PostsListResponse.Data.Child.Post>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostsListResponse.Data.Child.Post> {
        return try {
            val currentPage = params.key ?: 0
            val response = ApiClient.getPosts(lastAfter)
            val data = response.body()!!.data
            val postsList = mutableListOf<PostsListResponse.Data.Child.Post>()
            postsList.addAll(data.posts.map {
                val post = it.post
                post.created = calculateHoursAgo(post.created)
                return@map post
            })
            lastAfter = data.after

            LoadResult.Page(
                data = postsList,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }catch (e: HttpException) {
            LoadResult.Error(e)
        }


    }


    private fun calculateHoursAgo(created: Long): Long {
        val nowDate = Date().time / 1000
        val minutesPassed = (nowDate - created) / 60
        return minutesPassed / 60
    }
}