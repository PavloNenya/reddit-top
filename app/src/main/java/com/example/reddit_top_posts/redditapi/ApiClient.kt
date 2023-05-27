package com.example.reddit_top_posts.redditapi

import com.example.reddit_top_posts.config.BASE_URL
import com.example.reddit_top_posts.config.POSTS_NUMBER
import com.example.reddit_top_posts.redditapi.response.PostsListResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val apiService: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    suspend fun getPosts(
        after: String?
    ): Response<PostsListResponse> {
        val result = apiService.getPostsList(after)
//        println(result.body().toString())
        return result
    }
}