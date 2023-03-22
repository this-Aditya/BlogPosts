package com.aditya.blogexplorer.api

import com.aditya.blogexplorer.models.Post
import com.aditya.blogexplorer.models.User
import retrofit2.http.*


interface BlogApi {
    @GET("posts")
    suspend fun getPosts(
        @Query("_page") _page: Int = 1, @Query("_limit") _limit: Int = 10
    ): List<Post>

    @GET("posts/{id}")
    suspend fun getPost(
        @Path("id") postId: Int
    ): Post

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") Uid: Int
    ): User

    //updating the data using post method
    @PUT("posts/{id}")
    suspend fun updatePost(
        @Path("id") id: Int, @Body post: Post
    ): Post
    //deleting the post
    @DELETE("posts/{id}")
    suspend fun deletePost(
        @Path("id") postId: Int
    )
}