package com.example.careplace

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DoctorProfileSchedule : AppCompatActivity() {
    lateinit var info_btn : Button
    lateinit var review_btn : Button
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView
    lateinit var Doctorid :String
    lateinit var DoctorScheduel : ListView
    private lateinit var scheduleList2: MutableList<Schedule>
    lateinit var doctor_name : TextView
    lateinit var Doctor_spec : TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile_schedule)
        iniliaztion()
        clicking()
        Doctorid = intent.getStringExtra("DoctorId")!!
        retrieveSchedules()
        shareDoctorId()

        }
    private fun iniliaztion()
    {
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
        info_btn = findViewById(R.id.info_btn1)
        review_btn = findViewById(R.id.review_btn1)
        DoctorScheduel = findViewById(R.id.profile_appointments_list)
        doctor_name = findViewById(R.id.Doctor_Schedule_name)
        Doctor_spec = findViewById(R.id.Doctor_schedule_spec)
        scheduleList2 = mutableListOf()
    }
    private fun clicking()
    {
        info_btn.setOnClickListener {
            var my_intent2 = Intent(this, DoctorProfileInfo::class.java)
            startActivity(my_intent2)
        }
        review_btn.setOnClickListener {
            var myintent = Intent(this, DoctorProfileRiviews::class.java)
            startActivity(myintent)
        }
        home_btn.setOnClickListener {
            val myintent1 = Intent(this , Patient_Home_Screen::class.java)
            startActivity(myintent1)
        }
        setting_btn.setOnClickListener {
            val myintent2 = Intent(this ,Patient_Setting_Screen ::class.java)
            startActivity(myintent2)
        }
        calender_btn.setOnClickListener {
            val myintent4 = Intent(this , Patient_Calender_Screen::class.java)
            startActivity(myintent4)
        }
        chat_btn.setOnClickListener {
            val myintent5 = Intent(this ,ContactActivity_For_Patient ::class.java)
            startActivity(myintent5)
        }
        val doctorname = intent.getStringExtra("DoctorName")
        val doctorspec = intent.getStringExtra("Doctor_spec")
        doctor_name.text = doctorname
        Doctor_spec.text = doctorspec
    }
    private fun retrieveSchedules() {

        val mref = FirebaseDatabase.getInstance().getReference("Doctors_schedule").child(Doctorid).child("schedules")

        mref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                scheduleList2.clear() // Clear existing scheduleList

                for (scheduleSnapshot in dataSnapshot.children) {
                    val date = scheduleSnapshot.child("date").getValue(String::class.java)
                    val time = scheduleSnapshot.child("time").getValue(String::class.java)
                    val sid = scheduleSnapshot.child("sui").getValue(String::class.java)

                    if (date != null && time != null) {
                        val schedule = Schedule(date, time, sid!!)
                        scheduleList2.add(schedule) // array of scheduel_data_class
                    }
                }

                // Create and set adapter for ListView
                val adapter = ScheduleAdapter_user(this@DoctorProfileSchedule, scheduleList2)
                DoctorScheduel.adapter = adapter
                // number of scheduel_class_data -> schedulelist -> adpater -> schedulelistview.adpater
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors

                Toast.makeText(this@DoctorProfileSchedule, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun shareDoctorId()
    {
        val sharedPreferences = getSharedPreferences("share_data",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Doctorid",Doctorid)
        editor.apply()
    }


}