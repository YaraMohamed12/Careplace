package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DoctorProfileInfo : AppCompatActivity() {
    lateinit var info_btn : Button
    lateinit var review_btn : Button
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var Doctorid :String
    lateinit var doctor_name : TextView
    lateinit var Doctor_spec : TextView
    lateinit var chat_btn : ImageView


    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile_info)

        Doctorid = intent.getStringExtra("DoctorId")!!
        val doctorname = intent.getStringExtra("DoctorName")
        val doctorspec = intent.getStringExtra("Doctor_spec")
        info_btn = findViewById(R.id.info_btn1)
        review_btn = findViewById(R.id.review_btn1)
        doctor_name = findViewById(R.id.textView20)
        Doctor_spec = findViewById(R.id.textView21)
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
        doctor_name.text = doctorname
        Doctor_spec.text = doctorspec




            info_btn.setOnClickListener {
                var myintent = Intent(this, DoctorProfileInfo::class.java)
                startActivity(myintent)
            }

            review_btn.setOnClickListener {
                var myintent = Intent(this, DoctorProfileRiviews::class.java)
                myintent.putExtra("DoctorId",Doctorid)
                myintent.putExtra("DoctorName",doctorname)
                myintent.putExtra("Doctor_spec",doctorspec)
                startActivity(myintent)
            }

        home_btn.setOnClickListener {

            val myintent1 = Intent(this , Patient_Home_Screen::class.java)
            startActivity(myintent1)

        }

        setting_btn.setOnClickListener {

            val myintent2 = Intent(this , Patient_Setting_Screen::class.java)
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

            val myintent5 = Intent(this ,ContactActivity_For_Patient ::class.java)
            startActivity(myintent5)

        }

    }
}