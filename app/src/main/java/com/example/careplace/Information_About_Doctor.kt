package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Information_About_Doctor : AppCompatActivity() {
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView
    lateinit var aboutme_text : EditText
    lateinit var done_btn : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_about_doctor)
        inilaiztion()
        setuplistener()
        AboutmeDoc()
        ReadoctorABout()

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
        chat_btn.setOnClickListener {
            val myintent5 = Intent(this ,ContactActivity_For_Patient ::class.java)
            startActivity(myintent5)
        }

    }

    private fun inilaiztion() {
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
     aboutme_text = findViewById(R.id.editTextTextMultiLine)
        done_btn = findViewById(R.id.aboutme_donebtn)
    }
    private fun AboutmeDoc()
    {
        done_btn.setOnClickListener {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val database = FirebaseDatabase.getInstance()
            val aboutMeRef = database.getReference("DUser").child(userId!!).child("aboutMe")
            val about_string: kotlin.String = aboutme_text.text.toString()
            aboutMeRef.setValue(about_string)
        }
    }
    private fun ReadoctorABout()
    {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val aboutMeRef = database.getReference("DUser").child(userId!!).child("aboutMe")
        aboutMeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val aboutMe = snapshot.getValue(String::class.java)
                aboutme_text.setText(aboutMe)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Information_About_Doctor,"failed to retrive user Info", Toast.LENGTH_SHORT).show()
            }
        })
    }
}