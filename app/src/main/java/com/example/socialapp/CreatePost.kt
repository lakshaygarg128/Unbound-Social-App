package com.example.socialapp

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.example.socialapp.dao.PostDao
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class CreatePost : AppCompatActivity() {
    private var imageUrl: Uri? = null
    lateinit var filePath: Uri
    private var databaseReference: DatabaseReference? = null
    private var storageReference: StorageReference? = null



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

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.actionbar)))
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
            val progressBar : LottieAnimationView = findViewById(R.id.progressBar)
            progressBar.visibility = View.VISIBLE

            val ref = storageReference!!.child(

                    "upload/" + System.currentTimeMillis() + "." + getFileExt(filepath = filePath)
            )
           // PUT function
            ref.putFile(filePath).addOnSuccessListener { taskSnapshot ->
                val data = taskSnapshot.storage.downloadUrl

                while (!data.isSuccessful);
                imageUrl = data.result
                val imageFilePath = imageUrl.toString()
                val input = About_post.text.toString()
                if (input.isNotEmpty()) {
                    post.addPost(input, imageFilePath)
                    finish()
                }

            }.addOnFailureListener{
                Toast.makeText(this,"UPLOAD FAIL",Toast.LENGTH_SHORT).show()
            }
            
            
            

        }
    }

    private fun getFileExt(filepath: Uri): String? {
        val cr = contentResolver
        val map = MimeTypeMap.getSingleton()

        return map.getExtensionFromMimeType(cr.getType(filepath))

    }

    private fun choosepic() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 0)

    }

}