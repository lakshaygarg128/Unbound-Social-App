package com.example.socialapp.dao

import com.example.socialapp.model.Post
import com.example.socialapp.model.user
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db = FirebaseFirestore.getInstance()
    val postCollecction = db.collection("post")
    val CurrentAuth =Firebase.auth
    fun addPost (text :String)
    {
        val CurrentId = CurrentAuth.currentUser!!.uid
            val userdao = Usedao()
        GlobalScope.launch{
            var user =  userdao.getUserById(CurrentId).to(User::class.java).first.await().toObject(user::class.java)!!
            val current_time = System.currentTimeMillis()
            val post = Post(text,user,current_time)
            postCollecction.document().set(post)

        }
    }
    fun getpostbyid(id : String):Task<DocumentSnapshot>{
        return postCollecction.document(id).get()
    }
    fun updatelikes(id : String){
val currentuser = CurrentAuth.currentUser!!.uid
        GlobalScope.launch {
            val post = getpostbyid(id).await().toObject(Post::class.java)
            val liked=post!!.likedBy.contains(currentuser)
            if(liked)
            {
                post.likedBy.remove(currentuser)
            }else{
                post.likedBy.add(currentuser)
            }
            postCollecction.document(id).set(post)
        }
    }
}