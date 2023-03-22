package com.aditya.blogexplorer

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aditya.blogexplorer.databinding.ItemBlogPostBinding
import com.aditya.blogexplorer.models.Post

private const val TAG = "BlogAdapter"

class BlogPostAdapter(private val posts: List<Post>, private val itemClicked: ItemClickedListener) :
    RecyclerView.Adapter<BlogPostAdapter.ViewHolder>() {
    lateinit var itemView: ItemBlogPostBinding

    interface ItemClickedListener {
        fun onItemClicked(post: Post)
    }

    inner class ViewHolder(itemView: ItemBlogPostBinding) :
        RecyclerView.ViewHolder(itemView.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemView = ItemBlogPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        itemView.tvId.text = post.id.toString()
        itemView.tvTitle.text = post.title
        itemView.tvBlogBody.text = post.title
        Log.i(TAG, "onBindViewHolder: ")
        itemView.root.setOnClickListener {
            Log.i(TAG, "On clicked Listened ")
            itemClicked.onItemClicked(post)
        }
    }
}