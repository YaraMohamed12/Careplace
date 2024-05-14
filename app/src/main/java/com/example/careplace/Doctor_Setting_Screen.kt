package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Doctor_Setting_Screen : AppCompatActivity() {
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView
    lateinit var Log_out_btn : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_setting_screen)
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
        Log_out_btn = findViewById(R.id.Log_out_doctor)
        Log_out_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val myIntent = Intent(this, DocOrPat::class.java)
            // Add flags to clear the activity stack and prevent returning to the logout screen
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(myIntent)
            finish()
        }

        home_btn.setOnClickListener {

            val myintent1 = Intent(this , Doctor_Home_Screen::class.java)
            startActivity(myintent1)

        }

        setting_btn.setOnClickListener {

            val myintent2 = Intent(this ,Doctor_Setting_Screen ::class.java)
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

            val myintent4 = Intent(this , Doctor_Calender_Screen::class.java)
            startActivity(myintent4)

        }

        chat_btn.setOnClickListener {

            val myintent5 = Intent(this ,ContactActivity ::class.java)
            startActivity(myintent5)

        }

    }
}