package com.example.socialapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialapp.model.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Post_Adapter(options: FirestoreRecyclerOptions<Post> , val listner :IPostApdapter) : FirestoreRecyclerAdapter<Post,Post_Adapter.Post_viewholder>(options) {
    class Post_viewholder (ItemView :View ) : RecyclerView.ViewHolder(ItemView){
        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Post_viewholder {
      val viewholder =  Post_viewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_data,parent,false))
      viewholder.likeButton.setOnClickListener {
          listner.onlikeclicked(snapshots.getSnapshot(viewholder.adapterPosition).id)
      }
        return viewholder
    }

    override fun onBindViewHolder(holder: Post_Adapter.Post_viewholder, position: Int, model: Post) {
       holder.postText.text=model.text
        holder.userText.text=model.createdBy.displayname
        Glide.with(holder.userImage.context).load(model.createdBy.imageurl).circleCrop().into(holder.userImage)
        holder.likeCount.text=model.likedBy.size.toString()
        holder.createdAt.text=Utils.getTimeAgo(model.createdon)
        val auth = Firebase.auth
        val userid = auth.uid
        if(model.likedBy.contains(userid)){
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_liked))
        }else{
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_unliked))
        }
    }
  interface IPostApdapter{
      fun onlikeclicked(id : String)
      {

      }
  }
}