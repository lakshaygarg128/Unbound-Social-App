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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CreatePost : AppCompatActivity() {
    lateinit var filePath: Uri
    private var databaseReference: DatabaseReference? = null
    private var storageReference: StorageReference? = null

    private val REQUEST_CODE: Int =0

    private lateinit var post: PostDao
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            filePath = data?.data!!
            Toast.makeText(this,""+filePath,Toast.LENGTH_SHORT).show()
            val image_post = findViewById<ImageView>(R.id.image)
            image_post.setImageURI(filePath)
        } else {
            Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        val postButton: Button =findViewById(R.id.post)
        val About_post : EditText = findViewById(R.id.About_post)
        val choose : Button = findViewById(R.id.choose_button)
        databaseReference = FirebaseDatabase.getInstance().getReference("User_Details")
        storageReference = FirebaseStorage.getInstance().reference

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
        startActivityForResult(intent, 0)

    }

}