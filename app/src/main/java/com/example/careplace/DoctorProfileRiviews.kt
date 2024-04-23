package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DoctorProfileRiviews : AppCompatActivity() {
    lateinit var schedule_btn : Button
    lateinit var info_btn : Button
    lateinit var review_btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor_profile_riviews)

        schedule_btn = findViewById(R.id.schedule_btn)
        info_btn = findViewById(R.id.info_btn)
        review_btn = findViewById(R.id.review_btn)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        schedule_btn.setOnClickListener {
            var myintent = Intent(this, DoctorProfileSchedule::class.java)
            startActivity(myintent)
        }

        info_btn.setOnClickListener {
            var myintent = Intent(this, DoctorProfileInfo::class.java)
            startActivity(myintent)
        }

        review_btn.setOnClickListener {
            var myintent = Intent(this, DoctorProfileRiviews::class.java)
            startActivity(myintent)
        }
    }
}