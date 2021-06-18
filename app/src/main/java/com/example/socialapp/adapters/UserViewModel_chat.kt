package com.example.socialapp.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialapp.R
import com.example.socialapp.model.user

class UserViewModel_chat(itemView : View) : RecyclerView.ViewHolder(itemView)  {
    fun bind(user: user) =with(itemView){

        val titletv : TextView = findViewById(R.id.Title)
        val profileiv : ImageView =findViewById(R.id.Profile_chat_list)
        val timetv : TextView = findViewById(R.id.TimeTitle)
        val counttv : TextView= findViewById(R.id.Count_chat_tv)
        counttv.visibility = View.INVISIBLE
        timetv.visibility = View.INVISIBLE
        titletv.setText(user.displayname)
        Glide.with(profileiv.context).load(user.imageurl).into(profileiv)



    }
}