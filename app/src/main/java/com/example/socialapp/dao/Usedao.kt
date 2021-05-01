package com.example.socialapp.dao

import com.example.socialapp.model.user
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Document

class Usedao {
    val db  = FirebaseFirestore.getInstance()
    val usercollection = db.collection("users")
    fun addUser( user : user?){
       user?.let{
           GlobalScope.launch(Dispatchers.IO) {
               usercollection.document(user.userid).set(it)
           }

       }
    }
    fun getUserById(uID: String) : Task<DocumentSnapshot> {
        return usercollection.document(uID).get()
    }
    
}