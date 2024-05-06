package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Doctor_Home_Screen : AppCompatActivity() {
    lateinit var home_btn: ImageView
    lateinit var setting_btn: ImageView
    lateinit var your_profile_btn: ImageView
    lateinit var calender_btn: ImageView
    lateinit var chat_btn: ImageView
    lateinit var OpenResavtion : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_home_screen)
    inilaztionval()
        buttonlistener()




    }
    fun inilaztionval()
    {
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn = findViewById(R.id.goto_chat)
        OpenResavtion = findViewById(R.id.Open_schedule)
    }

    fun buttonlistener()
    {
        home_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val myIntent = Intent(this@Doctor_Home_Screen, DocOrPat::class.java)
            startActivity(myIntent)
            finish()

        }

        setting_btn.setOnClickListener {

            val myintent2 = Intent(this, Doctor_Setting_Screen::class.java)
            startActivity(myintent2)

        }

        chat_btn.setOnClickListener {

            val myintent5 = Intent(this, ContactActivity::class.java)
            startActivity(myintent5)

        }
     OpenResavtion.setOnClickListener {
         val myintent = Intent(this , Doctor_open_Schedule::class.java)
         startActivity(myintent)
     }
        calender_btn.setOnClickListener {

            val myintent = Intent(this , Doctor_Calender_Screen::class.java)
            startActivity(myintent)
        }


    }
}