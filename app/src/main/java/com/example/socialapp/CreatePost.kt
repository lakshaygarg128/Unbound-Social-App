package com.example.socialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.socialapp.dao.PostDao
import com.example.socialapp.model.Post

class CreatePost : AppCompatActivity() {
    private lateinit var post: PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        val postButton: Button =findViewById(R.id.post)
        val About_post : EditText = findViewById(R.id.About_post)

        post = PostDao()
        postButton.setOnClickListener{
          val input_about = About_post.text.toString().trim()
            if(!input_about.isEmpty())
            {
                post.addPost(input_about)
                finish()
            }
        }
    }
}