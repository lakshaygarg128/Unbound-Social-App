package com.example.socialapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Intro_Tour : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.intro1)
        )
        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.intro2)
        )
        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.intro3)
        )
        showStatusBar(false)
        setTransformer(AppIntroPageTransformerType.Depth)
        isWizardMode = true
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        val to_signin = Intent(this,Signin::class.java)
        startActivity(to_signin)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        val to_signin = Intent(this,Signin::class.java)
        startActivity(to_signin)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val auth = Firebase.auth
        if(auth.currentUser!=null){
            val tointro = Intent(this,Signin::class.java)
            startActivity(tointro)
            finish()
        }
    }

}
