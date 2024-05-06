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

class Doctor_Calender_Screen : AppCompatActivity() {
    private lateinit var home_btn: ImageView
    private lateinit var setting_btn: ImageView
    private lateinit var your_profile_btn: ImageView
    private lateinit var calender_btn: ImageView
    private lateinit var chat_btn: ImageView
    private lateinit var scheduleListView: ListView
    private lateinit var scheduleList: MutableList<Schedule>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_calender_screen)
        initialization()
        setClickListeners()

        // Initialize ListView and scheduleList
        scheduleListView = findViewById(R.id.schedule_list)
        scheduleList = mutableListOf()

        // Retrieve and display schedules
        retrieveSchedules()
    }

    private fun initialization() {
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn = findViewById(R.id.goto_chat)
    }

    private fun setClickListeners() {
        home_btn.setOnClickListener {
            startActivity(Intent(this, Doctor_Home_Screen::class.java))
        }

        setting_btn.setOnClickListener {
            startActivity(Intent(this, Patient_Setting_Screen::class.java))
        }

        calender_btn.setOnClickListener {
            // Current activity, no action needed
        }

        chat_btn.setOnClickListener {
            startActivity(Intent(this, ContactActivity::class.java))
        }
    }

    private fun retrieveSchedules() {
        val mAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser?.uid
        val mref = FirebaseDatabase.getInstance().getReference("Doctors_schedule").child(userId!!).child("schedules")

        mref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                scheduleList.clear() // Clear existing scheduleList

                for (scheduleSnapshot in dataSnapshot.children) {
                    val date = scheduleSnapshot.child("date").getValue(String::class.java)
                    val time = scheduleSnapshot.child("time").getValue(String::class.java)
                    val sid = scheduleSnapshot.child("sui").getValue(String::class.java)

                    if (date != null && time != null) {
                        val schedule = Schedule(date, time, sid!!)
                        scheduleList.add(schedule) // array of scheduel_data_class
                    }
                }

                // Create and set adapter for ListView
                val adapter = ScheduleAdapter(this@Doctor_Calender_Screen, scheduleList)
                scheduleListView.adapter = adapter
                // number of scheduel_class_data -> schedulelist -> adpater -> schedulelistview.adpater
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors

                Toast.makeText(this@Doctor_Calender_Screen, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
