package com.aditya.blogexplorer.edit

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.blogexplorer.api.RetrofitInstance
import com.aditya.blogexplorer.models.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "EditViewModel"

class EditViewModel : ViewModel() {

    private var _post: MutableLiveData<Post?> = MutableLiveData()
    val post: LiveData<Post?>
        get() = _post

    private var _currentStatus: MutableLiveData<ResultStatus> = MutableLiveData(ResultStatus.IDLE)
    val currentStatus: MutableLiveData<ResultStatus>
        get() = _currentStatus

    private var _error = MutableLiveData<String?> (null)
    val error:LiveData<String?>
    get() = _error

    private val _deleteStatus = MutableLiveData<Boolean>(false)
    val deleteStatus :LiveData<Boolean>
    get()= _deleteStatus

    fun updatePost(postId:Int, post:Post){
        viewModelScope.launch {
            try {
            _currentStatus.value = ResultStatus.WORKING
            _post.value = null
            val updatedPost = RetrofitInstance.api.updatePost(postId,post)
            Log.i(TAG, "Updated Post is $updatedPost")
            _post.value = updatedPost
            _currentStatus.value = ResultStatus.SUCCESS
            delay(500)
            _currentStatus.value = ResultStatus.IDLE
        } catch (e:Exception){
            _error.value = e.message
            _currentStatus.value = ResultStatus.ERROR
        }
        }
    }
    fun deletePost(postId:Int){
        viewModelScope.launch {
        try {
            _error.value = null
            _currentStatus.value = ResultStatus.WORKING
            RetrofitInstance.api.deletePost(postId)
            _deleteStatus.value = true
            _currentStatus.value = ResultStatus.SUCCESS
            delay(500)
            _currentStatus.value = ResultStatus.IDLE
        }catch (e:Exception){
            _error.value = e.message
            _currentStatus.value = ResultStatus.ERROR
            delay(500)
            _currentStatus.value = ResultStatus.IDLE
        }
        }
    }
}