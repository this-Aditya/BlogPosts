package com.aditya.blogexplorer.edit

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aditya.blogexplorer.EXTRA_POST
import com.aditya.blogexplorer.R
import com.aditya.blogexplorer.databinding.ActivityEditBinding
import com.aditya.blogexplorer.models.Post

private const val TAG = "EditActivity"

class EditActivity : AppCompatActivity() {
    private lateinit var viewModel: EditViewModel
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = intent.getSerializableExtra(EXTRA_POST) as Post
        binding.etTitle.setText(post.title.toString())
        binding.etContent.setText(post.body.toString())
        title = "Editing Post ${post.id}"
        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)

        viewModel.post.observe(this,Observer{updatedPost->
            if (updatedPost==null){
                binding.clPostResult.visibility = View.GONE
                return@Observer
            }
            else{
                binding.tvUpdatedTitle.text = updatedPost.title
                binding.tvUpdatedContent.text = updatedPost.body
                binding.clPostResult.visibility = View.VISIBLE
            }
        })

        viewModel.currentStatus.observe(this, Observer{ currentStatus->
            when(currentStatus){
                ResultStatus.IDLE ->{
                    binding.tvStatus.text = getString(R.string.idle)
                    binding.tvStatus.setTextColor(Color.GRAY)
                }
                ResultStatus.WORKING -> {
                    binding.tvStatus.text = getString(R.string.working)
                    binding.tvStatus.setTextColor(Color.MAGENTA)
                }
                ResultStatus.SUCCESS -> {
                    binding.tvStatus.text = getString(R.string.success)
                    binding.tvStatus.setTextColor(Color.GREEN)
                }
                ResultStatus.ERROR ->{
                    binding.tvStatus.text = getString(R.string.error)
                    binding.tvStatus.setTextColor(Color.RED)
                }
                else -> throw (IllegalStateException("Unexpected Result State found "))
            }
        })

        viewModel.error.observe(this, Observer {error->
            if (error==null){
                return@Observer
            }
            Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show()
        })

        binding.btnUpdatePut.setOnClickListener {
            Log.i(TAG, "Update Via Put")
            viewModel.updatePost(post.id,Post(post.userId,post.id,binding.etTitle.text.toString() ,binding.etContent.text.toString()))
        }
        binding.btnDelete.setOnClickListener {
            viewModel.deletePost(post.id)
            binding.clPostResult.visibility = View.GONE
        }

    }
}