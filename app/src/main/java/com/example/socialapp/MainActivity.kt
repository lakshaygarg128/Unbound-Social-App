package com.example.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.dao.PostDao
import com.example.socialapp.model.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity(), Post_Adapter.IPostApdapter {

    private lateinit var postDao: PostDao
    lateinit var adapter: Post_Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            val fab_button : View = findViewById(R.id.fab)
        fab_button.setOnClickListener{
          val intent = Intent(this, CreatePost::class.java)
            startActivity(intent)
        }
        setupRecyclerview()

    }

    private fun setupRecyclerview() {
        postDao = PostDao()
        val postcollection = postDao.postCollecction
        val query = postcollection.orderBy("createdon",Query.Direction.DESCENDING)
        val RecyclerViewOption = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
    adapter= Post_Adapter(RecyclerViewOption ,this)
        var recyclerView : RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onlikeclicked(id: String) {
     postDao.updatelikes(id)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}