package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Patient_Setting_Screen : AppCompatActivity() {
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView
    lateinit var reset_btn : Button
    lateinit var log_out_btn : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_setting_screen)

        inilaztionlistenr()
        clicking()

    }

    private fun inilaztionlistenr() {
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
        reset_btn = findViewById(R.id.reset_pass_btn)
        log_out_btn = findViewById(R.id.Log_out)
    }

    private fun clicking(){
        home_btn.setOnClickListener {
            val myintent1 = Intent(this , Patient_Home_Screen::class.java)
            startActivity(myintent1)
        }
        setting_btn.setOnClickListener {
            val myintent2 = Intent(this ,Patient_Setting_Screen ::class.java)
            startActivity(myintent2)
        }
        your_profile_btn.setOnClickListener {
            val myintent3 = Intent(this , My_Profile_Details_for_patient::class.java)
            startActivity(myintent3)
        }
        calender_btn.setOnClickListener {
            val myintent4 = Intent(this , Patient_Calender_Screen::class.java)
            startActivity(myintent4)
        }
        chat_btn.setOnClickListener {

            val myintent5 = Intent(this ,ContactActivity ::class.java)
            startActivity(myintent5)

        }
        reset_btn.setOnClickListener {
            val intent6 = Intent(this, Reset_Password::class.java)
            startActivity(intent6)
        }
        log_out_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut() // Sign out the current user
            val myIntent = Intent(this, DocOrPat::class.java)
            // Add flags to clear the activity stack and prevent returning to the logout screen
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(myIntent)
            finish()
        }
    }
}