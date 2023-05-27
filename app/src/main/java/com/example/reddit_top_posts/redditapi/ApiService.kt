package com.example.reddit_top_posts.redditapi

import com.example.reddit_top_posts.config.POSTS_NUMBER
import com.example.reddit_top_posts.redditapi.response.PostsListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/top.json")
    suspend fun getPostsList(
        @Query("after") after: String?,
        @Query("limit") limit: Int? = POSTS_NUMBER
    ): Response<PostsListResponse>
}