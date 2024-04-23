package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DoctorProfileSchedule : AppCompatActivity() {
    lateinit var schedule_btn : Button
    lateinit var info_btn : Button
    lateinit var review_btn : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_doctor_profile_schedule)
        schedule_btn = findViewById(R.id.schedule_btn1)
        info_btn = findViewById(R.id.info_btn1)
        review_btn = findViewById(R.id.review_btn1)

        schedule_btn.setOnClickListener {
            var my_intent1 = Intent(this, DoctorProfileSchedule::class.java)
            startActivity(my_intent1)
        }

        info_btn.setOnClickListener {
            var my_intent2 = Intent(this, DoctorProfileInfo::class.java)
            startActivity(my_intent2)
        }

        review_btn.setOnClickListener {
            var myintent = Intent(this, DoctorProfileRiviews::class.java)
            startActivity(myintent)
        }


        }


}