package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Patient_Calender_Screen : AppCompatActivity() {
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var chat_btn : ImageView
    lateinit var DoctorlistView: ListView
     lateinit var DoctorList: MutableList<Appointment>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_calender_screen)
          inilaztion()
        setuplistener()
        retrieveDoctors()
        DoctorList = mutableListOf()

    }
    private fun inilaztion()
    { home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
        DoctorlistView = findViewById(R.id.User_date_list)
    }
    private fun setuplistener()
    {
        home_btn.setOnClickListener {
            val myintent1 = Intent(this , Patient_Home_Screen::class.java)
            startActivity(myintent1)
        }
        setting_btn.setOnClickListener {
            val myintent2 = Intent(this ,Patient_Setting_Screen ::class.java)
            startActivity(myintent2)
        }
        chat_btn.setOnClickListener {
            val myintent5 = Intent(this ,ContactActivity_For_Patient ::class.java)
            startActivity(myintent5)
        }
        your_profile_btn.setOnClickListener {
            val myintent6 = Intent(this ,My_Profile_Details_for_patient ::class.java)
            startActivity(myintent6)
        }
    }
    private fun retrieveDoctors() {
        val mAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser?.uid
        val mref = FirebaseDatabase.getInstance().getReference("user").child(userId!!).child("userbook")

        mref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                DoctorList.clear() // Clear existing scheduleList

                for (scheduleSnapshot in dataSnapshot.children) {
                    val date = scheduleSnapshot.child("schedule").child("date").getValue(String::class.java)
                    val time = scheduleSnapshot.child("schedule").child("time").getValue(String::class.java)
                    val sid = scheduleSnapshot.child("schedule").child("sui").getValue(String::class.java)
                    val Doctorid = scheduleSnapshot.child("userid").getValue(String::class.java)

                    if (date != null && time != null) {
                        val schedule = Schedule(date, time, sid!!)
                        val AppoimentData = Appointment(schedule,Doctorid!!)

                        DoctorList.add(AppoimentData) // array of scheduel_data_class
                    }
                }


                val adapter = Booked_Doctor_Adapter(this@Patient_Calender_Screen, DoctorList)
                DoctorlistView.adapter = adapter

            }

            override fun onCancelled(databaseError: DatabaseError) {


                Toast.makeText(this@Patient_Calender_Screen, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })
    }
}