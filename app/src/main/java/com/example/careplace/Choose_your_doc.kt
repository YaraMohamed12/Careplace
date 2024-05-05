package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.careplace.databinding.ActivityChooseYourDocBinding

class Choose_your_doc : AppCompatActivity() {
    lateinit var select_spec1 : FrameLayout
    lateinit var binding: ActivityChooseYourDocBinding
    lateinit var mySpec : String
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseYourDocBinding.inflate(layoutInflater)
        setContentView(binding.root)
        select_spec1 = findViewById(R.id.spec1_frame)
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

            val myintent2 = Intent(this , Patient_Setting_Screen::class.java)
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

        select_spec1.setOnClickListener {
            mySpec = "Cardiologist"
            val myIntent = Intent(this, Doctors_List::class.java)
            myIntent.putExtra("Spec",mySpec)
            startActivity(myIntent)
        }
        binding.spec2Frame.setOnClickListener {
            mySpec = "Pulmonologist"
            val myIntent = Intent(this, Doctors_List::class.java)
            myIntent.putExtra("Spec",mySpec)
            startActivity(myIntent)
        }

    }
}