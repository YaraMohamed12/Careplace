package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Patient_Calender_Screen : AppCompatActivity() {
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_calender_screen)
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)


        home_btn.setOnClickListener {

            val myintent1 = Intent(this , Patient_Home_Screen::class.java)
            startActivity(myintent1)

        }

        setting_btn.setOnClickListener {

            val myintent2 = Intent(this ,SettingScreen ::class.java)
            startActivity(myintent2)

        }
//
//        your_profile_btn.setOnClickListener {
//
//            val myintent3 = Intent(this , ::class.java)
//            startActivity(myintent3)
//
//        }
//
        calender_btn.setOnClickListener {

            val myintent4 = Intent(this ,Patient_Calender_Screen ::class.java)
            startActivity(myintent4)

        }

        chat_btn.setOnClickListener {

            val myintent5 = Intent(this ,ContactActivity ::class.java)
            startActivity(myintent5)

        }



    }
}