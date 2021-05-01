package com.example.socialapp.model

import com.google.firebase.firestore.auth.User

class Post (val text :String="",
            val createdBy : user = user(),
            val createdon : Long = 0L,
            val likedBy :ArrayList<String> = ArrayList())