package com.example.socialapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialapp.model.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class Post_Adapter(options: FirestoreRecyclerOptions<Post>) : FirestoreRecyclerAdapter<Post,Post_Adapter.Post_viewholder>(options) {
    class Post_viewholder (ItemView :View ) : RecyclerView.ViewHolder(ItemView){
        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Post_viewholder {
       return Post_viewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_data,parent,false))
    }

    override fun onBindViewHolder(holder: Post_viewholder, position: Int, model: Post) {
       holder.postText.text=model.text
        holder.userText.text=model.createdBy.displayname
        Glide.with(holder.userImage.context).load(model.createdBy.imageurl).circleCrop().into(holder.userImage)
        holder.likeCount.text=model.likedBy.size.toString()
        holder.createdAt.text=Utils.getTimeAgo(model.createdon)
    }

}