package com.example.socialapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.socialapp.dao.PostDao
import com.example.socialapp.model.Post
import javax.annotation.Nullable

class CreatePost : AppCompatActivity() {
    lateinit var imageuri: Uri
    private val REQUEST_CODE: Int =1
    val image_post = findViewById<ImageView>(R.id.image)
    private lateinit var post: PostDao
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        val postButton: Button =findViewById(R.id.post)
        val About_post : EditText = findViewById(R.id.About_post)
        val choose : Button = findViewById(R.id.choose_button)

        post = PostDao()
        choose.setOnClickListener {
            choosepic()
        }
        postButton.setOnClickListener{
          val input_about = About_post.text.toString().trim()
            if(!input_about.isEmpty())
            {
                post.addPost(input_about)
                finish()
            }
        }
    }

    private fun choosepic() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_CODE)

    }

}