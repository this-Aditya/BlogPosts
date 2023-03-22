package com.aditya.blogexplorer.api

import com.aditya.blogexplorer.models.Post
import com.aditya.blogexplorer.models.User
<<<<<<< Updated upstream
=======
import retrofit2.http.Body
import retrofit2.http.DELETE
>>>>>>> Stashed changes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface BlogApi {
    @GET("posts")
    suspend fun getPosts(
        @Query("_page") _page: Int = 1,
        @Query("_limit") _limit: Int = 10
    ): List<Post>

    @GET("posts/{id}")
    suspend fun getPost(
        @Path("id") postId: Int
    ): Post

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") Uid: Int
    ): User
<<<<<<< Updated upstream
=======

    //updating the data using put method
    @PUT("posts/{id}")
    suspend fun updatePost(
        @Path("id") id: Int, @Body post: Post
    ): Post
>>>>>>> Stashed changes
}