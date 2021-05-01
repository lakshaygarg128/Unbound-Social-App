package com.example.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            val fab_button : View = findViewById(R.id.fab)
        fab_button.setOnClickListener{
          val intent = Intent(this, CreatePost::class.java)
            startActivity(intent)


        }
    }
}