package com.example.reddit_top_posts.api

import com.example.reddit_top_posts.config.BASE_URL
import com.example.reddit_top_posts.api.response.PostsListResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val apiService: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    suspend fun getPosts(
        after: String?
    ): Response<PostsListResponse> {
        return apiService.getPostsList(after)
    }

}