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

class Patient_List : AppCompatActivity() {
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView
    lateinit var PatientListview : ListView
    lateinit var Patientlist : MutableList<Appointment>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_list)
        inilaiztion()
        setuplistener()
      Patientlist = mutableListOf()
     retrievePatients()



    }
    private fun inilaiztion(){
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
        PatientListview = findViewById(R.id.Docotor_pateint_list)
    }
    private fun setuplistener(){

        home_btn.setOnClickListener {

            val myintent1 = Intent(this , Doctor_Home_Screen::class.java)
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

            val myintent4 = Intent(this , Patient_Calender_Screen::class.java)
            startActivity(myintent4)

        }

        chat_btn.setOnClickListener {

            val myintent5 = Intent(this ,ContactActivity ::class.java)
            startActivity(myintent5)

        }


    }
    private fun retrievePatients() {
        val mAuth = FirebaseAuth.getInstance()
        val Doctorid = mAuth.currentUser?.uid
        val mref = FirebaseDatabase.getInstance().getReference("Doctor_Booked").child(Doctorid!!).child("userbook")

        mref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Patientlist.clear() // Clear existing scheduleList

                for (scheduleSnapshot in dataSnapshot.children) {
                    val date = scheduleSnapshot.child("schedule").child("date").getValue(String::class.java)
                    val time = scheduleSnapshot.child("schedule").child("time").getValue(String::class.java)
                    val sid = scheduleSnapshot.child("schedule").child("sui").getValue(String::class.java)
                    val Patientid = scheduleSnapshot.child("userid").getValue(String::class.java)

                    if (date != null && time != null) {
                        val schedule = Schedule(date, time, sid!!)
                        val AppoimentData = Appointment(schedule,Patientid!!)

                        Patientlist.add(AppoimentData) // array of scheduel_data_class
                    }
                }


                val adapter = Booking_Patient_Adapter(this@Patient_List, Patientlist)
                PatientListview.adapter = adapter

            }

            override fun onCancelled(databaseError: DatabaseError) {


                Toast.makeText(this@Patient_List, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })
    }

}