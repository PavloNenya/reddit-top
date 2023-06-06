package com.example.reddit_top_posts.api.response

import com.google.gson.annotations.SerializedName

data class PostsListResponse(
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("before")
        val before: String,
        @SerializedName("after")
        val after: String,
        @SerializedName("dist")
        val dist: Int,
        @SerializedName("children")
        val posts: List<Child>
    ) {
        data class Child(
            @SerializedName("data")
            val post: Post
        ) {
            data class Post(
                @SerializedName("id")
                val id: String,
                @SerializedName("title")
                val title: String,
                @SerializedName("thumbnail")
                val thumbnail: String,
                @SerializedName("url")
                val url: String,
                @SerializedName("author")
                val author: String,
                @SerializedName("created")
                var created: Long,
                @SerializedName("num_comments")
                val num_comments: Int
            )
        }
    }
}