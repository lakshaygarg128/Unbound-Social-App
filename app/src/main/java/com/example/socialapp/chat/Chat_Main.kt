package com.example.socialapp.chat

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.socialapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class Chat_Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.actionbar)))
        setContentView(R.layout.activity_chat__main)
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.chat_bottom_navigation)
     //    val fl : FrameLayout = findViewById(R.id.chat_frame_layout)
       
        val people_fragment = List_People()
        val chat_frament = Chating_Fragment()
        setCurrentFragment(chat_frament)

        bottomNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.Chat_button_menu_bottomNavigation->setCurrentFragment(chat_frament)
                R.id.People_button_bottomNavigation -> setCurrentFragment(people_fragment)

            }
            true
        }
    }

    private fun setCurrentFragment(fragment_select: Fragment)=
            supportFragmentManager.beginTransaction().apply {
        replace(R.id.chat_frame_layout,fragment_select)
        commit()
    }
}