package com.example.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.dao.PostDao
import com.example.socialapp.model.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), Post_Adapter.IPostApdapter {
   lateinit var  nav : NavigationView
   lateinit var toogle : ActionBarDrawerToggle
   lateinit var drawer : DrawerLayout
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

        //Drawer Code
        nav = findViewById(R.id.nav)
        drawer = findViewById(R.id.drawer_layout)
        toogle = ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close)
        drawer.addDrawerListener(toogle)
        toogle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Logout -> {
                    var auth = FirebaseAuth.getInstance()
                    Firebase.auth.signOut()
                    val intent : Intent = Intent(this,Signin::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item))
        {
            return true
        }

        return super.onOptionsItemSelected(item)
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