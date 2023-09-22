package com.aditya.blogexplorer

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.blogexplorer.databinding.ItemBlogPostBinding
import com.aditya.blogexplorer.models.Post

private const val TAG = "BlogAdapter"

class BlogPostAdapter(private val posts: List<Post>, private val itemClicked: ItemClickedListener) :
    RecyclerView.Adapter<BlogPostAdapter.BlogViewHolder>() {
    lateinit var itemView: ItemBlogPostBinding

    interface ItemClickedListener {
        fun onItemClicked(post: Post)
    }

    inner class BlogViewHolder(val holderView: ItemBlogPostBinding) :
        RecyclerView.ViewHolder(holderView.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        itemView = ItemBlogPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BlogViewHolder(itemView)
    }


    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val post = posts[position]

        holder.holderView.tvId.text = post.id.toString()
        holder.holderView.tvTitle.text = post.title
        holder.holderView.tvBlogBody.text = post.title
        Log.i(TAG, "onBindViewHolder: ")
        holder.holderView.root.setOnClickListener {
            Log.i(TAG, "On clicked Listened ")
            itemClicked.onItemClicked(post)
        }
    }
}