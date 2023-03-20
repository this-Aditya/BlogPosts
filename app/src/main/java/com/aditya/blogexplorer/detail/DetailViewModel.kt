package com.aditya.blogexplorer.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.blogexplorer.api.RetrofitInstance
import com.aditya.blogexplorer.models.Post
import com.aditya.blogexplorer.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "DetailViewModel"

class DetailViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = _post

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


    fun getPostDetails(postId: Int) {
        val api = RetrofitInstance.api
        viewModelScope.launch {
            val fetchedPost = api.getPost(postId)
            _user.value = api.getUser(fetchedPost.userId)
            _post.value = fetchedPost
        }
    }
}

