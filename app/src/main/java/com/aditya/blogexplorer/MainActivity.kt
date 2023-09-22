package com.aditya.blogexplorer

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.blogexplorer.models.Post
import com.aditya.blogexplorer.databinding.ActivityMainBinding
import kotlin.system.exitProcess

private const val TAG = "MainActivity"
const val EXTRA_ID = "GET_ID"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val blogPosts: MutableList<Post> = mutableListOf()
    private lateinit var blogPostAdapter: BlogPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Set up the RecyclerView
        blogPostAdapter = BlogPostAdapter( blogPosts,
            object:BlogPostAdapter.ItemClickedListener{
                override fun onItemClicked(post: Post) {
                    val intent = Intent(this@MainActivity,DetailActivity::class.java)
                    intent.putExtra(EXTRA_ID,post.id)
                    startActivity(intent)
                }
            }
        )
        binding.rvPosts.adapter = blogPostAdapter
        binding.rvPosts.layoutManager = LinearLayoutManager(this)

        // Observe the posts and update the adapter when the posts change
        viewModel.posts.observe(this, Observer { posts ->
            blogPosts.clear()
            blogPosts.addAll(posts)
            blogPostAdapter.notifyDataSetChanged()
            binding.rvPosts.smoothScrollToPosition(blogPosts.size-1)
                  })

        // Observe the loading state and show/hide the progress bar accordingly
        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        //observe the error
        viewModel.errorMessage.observe(this,Observer{error ->
            if(error == null){
                binding.tvError.visibility = View.GONE
            }
            else{
                binding.tvError.visibility = View.VISIBLE
                Toast.makeText(this,error,Toast.LENGTH_LONG).show()
            }
        })

        // Fetch the posts when the button is clicked
        binding.button.setOnClickListener {
            viewModel.getposts()
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Are you sure want to quit?")
                    .setMessage("Your current progress will be lost. Click yes if you are sure for quitting, else press no. Thanks!")
                    .setPositiveButton("Yes"){ _, _ ->
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_HOME)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        exitProcess(0)
                    }
                    .setNegativeButton("No") {dialogue,_ ->
                        dialogue.dismiss()
                    }.show()
            }
        }
        onBackPressedDispatcher.addCallback(this@MainActivity, callback)
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }
}
