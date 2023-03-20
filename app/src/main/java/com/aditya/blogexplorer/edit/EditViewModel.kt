package com.aditya.blogexplorer.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.blogexplorer.api.RetrofitInstance
import com.aditya.blogexplorer.models.Post
import kotlinx.coroutines.launch

private const val TAG = "EditViewModel"

class EditViewModel : ViewModel() {

    private var _post: MutableLiveData<Post?> = MutableLiveData()
    val post: LiveData<Post?>
        get() = _post

    private var _currentStatus: MutableLiveData<ResultStatus> = MutableLiveData(ResultStatus.IDLE)
    val currentStatus: MutableLiveData<ResultStatus>
        get() = _currentStatus

    fun updatePost(postId:Int, post:Post){
        viewModelScope.launch {
            _currentStatus.value = ResultStatus.WORKING
            _post.value = null
            val updatedPost = RetrofitInstance.api.updatePost(postId,post)
            Log.i(TAG, "Updated Post is $updatedPost")
            _post.value = updatedPost
            _currentStatus.value = ResultStatus.SUCCESS
        }
    }
}