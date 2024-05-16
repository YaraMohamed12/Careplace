package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContactActivity_For_Doctor : AppCompatActivity() {
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_for_doctor)
        inilaiztion()
        setuplistener()
    }

    private fun inilaiztion() {
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
    }

    private fun setuplistener() {
        home_btn.setOnClickListener {
            val myintent1 = Intent(this , Doctor_Home_Screen::class.java)
            startActivity(myintent1)
        }
        setting_btn.setOnClickListener {
            val myintent2 = Intent(this , Doctor_Setting_Screen::class.java)
            startActivity(myintent2)
        }
        your_profile_btn.setOnClickListener {
            val myintent3 = Intent(this , My_Profile_Details_for_doc::class.java)
            startActivity(myintent3)
        }
        calender_btn.setOnClickListener {
            val myintent4 = Intent(this , Doctor_Calender_Screen::class.java)
            startActivity(myintent4)
        }
    }
}