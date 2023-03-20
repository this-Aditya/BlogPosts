package com.aditya.blogexplorer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.blogexplorer.api.RetrofitInstance
import com.aditya.blogexplorer.models.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>>
        get() = _posts

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private var pageNumber = 1

    fun getposts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val fetchedPosts = RetrofitInstance.api.getPosts(pageNumber)
                pageNumber += 1
                val currentPosts = _posts.value ?: emptyList()
                _posts.value = currentPosts + fetchedPosts
                Log.i(TAG, "${_posts}")
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}