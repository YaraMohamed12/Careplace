package com.example.careplace

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DoctorProfileSchedule : AppCompatActivity() {
    lateinit var info_btn: Button
    lateinit var review_btn: Button
    lateinit var home_btn: ImageView
    lateinit var setting_btn: ImageView
    lateinit var your_profile_btn: ImageView
    lateinit var calender_btn: ImageView
    lateinit var chat_btn: ImageView
    lateinit var Doctorid: kotlin.String
    lateinit var DoctorScheduel: ListView
    private lateinit var scheduleList2: MutableList<Schedule>
    lateinit var doctor_name: TextView
    lateinit var Doctor_spec: TextView
    lateinit var doctorname: kotlin.String
    lateinit var doctorspec: kotlin.String
    lateinit var Doctor_loc: kotlin.String
    lateinit var rate_btn: Button
    lateinit var doc_locat: TextView
    lateinit var ratingBar: RatingBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile_schedule)
        iniliaztion()
        clicking()
        retrieveDoctorInfo()
        retrieveSchedules()
        shareDoctorId()
        RateMyDoctor()
        Readdocrate()
    }

    private fun iniliaztion() {
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn = findViewById(R.id.goto_chat)
        info_btn = findViewById(R.id.info_btn1)
        review_btn = findViewById(R.id.review_btn1)
        DoctorScheduel = findViewById(R.id.profile_appointments_list)
        doctor_name = findViewById(R.id.Doctor_Schedule_name)
        Doctor_spec = findViewById(R.id.Doctor_schedule_spec)
        scheduleList2 = mutableListOf()
        rate_btn = findViewById(R.id.Rate_btn)
        doc_locat = findViewById(R.id.Doctor_schedule_loc)
        ratingBar = findViewById(R.id.ratingBar)
    }

    private fun clicking() {
        info_btn.setOnClickListener {
            val myIntent2 = Intent(this, DoctorProfileInfo::class.java)
            myIntent2.putExtra("DoctorId", Doctorid)
            myIntent2.putExtra("DoctorName", doctorname)
            myIntent2.putExtra("Doctor_spec", doctorspec)
            myIntent2.putExtra("Doctor_loc",Doctor_loc)
            startActivity(myIntent2)
        }
        review_btn.setOnClickListener {
            val myIntent = Intent(this, DoctorProfileRiviews::class.java)
            myIntent.putExtra("DoctorId", Doctorid)
            myIntent.putExtra("DoctorName", doctorname)
            myIntent.putExtra("Doctor_spec", doctorspec)
            myIntent.putExtra("Doctor_loc",Doctor_loc)
            startActivity(myIntent)
        }
        home_btn.setOnClickListener {
            val myIntent1 = Intent(this, Patient_Home_Screen::class.java)
            startActivity(myIntent1)
        }
        setting_btn.setOnClickListener {
            val myIntent2 = Intent(this, Patient_Setting_Screen::class.java)
            startActivity(myIntent2)
        }
        calender_btn.setOnClickListener {
            val myIntent4 = Intent(this, Patient_Calender_Screen::class.java)
            startActivity(myIntent4)
        }
        chat_btn.setOnClickListener {
            val myIntent5 = Intent(this, ContactActivity_For_Patient::class.java)
            startActivity(myIntent5)
        }
    }

    private fun retrieveDoctorInfo() {
        // Ensure to check for null values before using intent extras
        Doctorid = intent.getStringExtra("DoctorId") ?: ""
        doctorname = intent.getStringExtra("DoctorName") ?: "Unknown Doctor"
        doctorspec = intent.getStringExtra("Doctor_spec") ?: "Unknown Specialty"
        Doctor_loc = intent.getStringExtra("Doctor_loc") ?: "Unknown Location"

        doctor_name.text = doctorname
        Doctor_spec.text = doctorspec
        doc_locat.text = Doctor_loc
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

                    if (date != null && time != null && sid != null) {
                        val schedule = Schedule(date, time, sid)
                        scheduleList2.add(schedule) // Add to schedule list
                    }
                }

                // Create and set adapter for ListView
                val adapter = ScheduleAdapter_user(this@DoctorProfileSchedule, scheduleList2)
                DoctorScheduel.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@DoctorProfileSchedule, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun shareDoctorId() {
        val sharedPreferences = getSharedPreferences("share_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Doctorid", Doctorid)
        editor.apply()
    }

    private fun RateMyDoctor() {
        rate_btn.setOnClickListener {
            val ratingValue = ratingBar.rating
            val database = FirebaseDatabase.getInstance()
            val ratingsRef = database.getReference("doctor_ratings").child(Doctorid)
            ratingsRef.setValue(ratingValue)
                .addOnSuccessListener {
                    Toast.makeText(this, "Your rate is saved", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Your rate failed to save", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun Readdocrate() {
        val database = FirebaseDatabase.getInstance()
        val ratingsRef = database.getReference("doctor_ratings").child(Doctorid)
        ratingsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val ratingValue = snapshot.getValue(Float::class.java)
                    if (ratingValue != null) {
                        ratingBar.rating = ratingValue
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DoctorProfileSchedule, "Failed to retrieve rating", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
