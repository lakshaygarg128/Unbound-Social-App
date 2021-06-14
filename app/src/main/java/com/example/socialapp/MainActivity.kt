package com.example.socialapp

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialapp.chat.Chat_Main
import com.example.socialapp.dao.PostDao
import com.example.socialapp.model.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), Post_Adapter.IPostApdapter {
   lateinit var  nav : NavigationView
   lateinit var nav_header : ImageView
   lateinit var toogle : ActionBarDrawerToggle
   lateinit var drawer : DrawerLayout
   private lateinit var postDao: PostDao
   lateinit var adapter: Post_Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.actionbar)))
        setContentView(R.layout.activity_main)
        // Add Post Button in post view
        val fab_button : View = findViewById(R.id.fab)
        fab_button.setOnClickListener{
            val intent = Intent(this, CreatePost::class.java)
            startActivity(intent)
        }
        // Setting up recycler View
        setupRecyclerview()

        //Drawer Code
        nav = findViewById(R.id.nav)
        drawer = findViewById(R.id.drawer_layout)
        toogle = ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close)
        drawer.addDrawerListener(toogle)
        toogle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val header_view = nav.getHeaderView(0)
        val profile_image : ImageView = header_view.findViewById(R.id.profileImage)
        val profile_name : TextView = header_view.findViewById(R.id.profile_name)
        val currentuser_prifileimage_url = Firebase.auth.currentUser!!.photoUrl
        val currentuser_prifileName = Firebase.auth.currentUser!!.displayName
        profile_name.setText(currentuser_prifileName)
        Glide.with(profile_image.context).load(currentuser_prifileimage_url).circleCrop().into(profile_image)



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
        var code=item.itemId
        if(toogle.onOptionsItemSelected(item))
        {
            return true
        }
        if(code==R.id.chatButton)
        {
            call_chat()
            Toast.makeText(this,"chat will open ",Toast.LENGTH_LONG).show()
        }

        return super.onOptionsItemSelected(item)
    }

     fun call_chat() {
        val intent = Intent(this,Chat_Main::class.java)
        startActivity(intent)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}