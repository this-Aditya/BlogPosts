package com.aditya.blogexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.blogexplorer.models.Post
import com.aditya.blogexplorer.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
const val EXTRA_ID = "GET_ID"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val blogPosts: MutableList<Post> = mutableListOf()
    private lateinit var blogPostAdapter: BlogPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Set up the RecyclerView
        blogPostAdapter = BlogPostAdapter(this, blogPosts,
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
    }
}
