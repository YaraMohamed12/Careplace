package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.String

class Doctor_open_Schedule : AppCompatActivity() {
    private lateinit var selected_date: CalendarView
    private lateinit var selected_time: TimePicker
    private lateinit var sumbit_btn: Button
    private lateinit var mref: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView
    private var selectedDate : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_open_schedule)
        initialization()
        SetupListner()
        selected_date.setOnDateChangeListener { view, year, month, dayOfMonth ->
             selectedDate = formatDate(year, month, dayOfMonth)
            Toast.makeText(this, "Selected date: $selectedDate", Toast.LENGTH_SHORT).show()
        }

        sumbit_btn.setOnClickListener {
            submitData()
        }

    }

    private fun SetupListner() {
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


    private fun formattingTime(timePicker: TimePicker): String {
        val hour = timePicker.hour
        val minute = timePicker.minute
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }


    private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun initialization() {
        selected_date = findViewById(R.id.schudel_calendar)
        selected_time = findViewById(R.id.schudel_time)
        sumbit_btn = findViewById(R.id.create_schedule_btn)
        mref = FirebaseDatabase.getInstance().getReference()
        mAuth = FirebaseAuth.getInstance()
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
        currentUser = mAuth.currentUser!!
    }

    private fun submitData() {
        // data of booking (time , date, sid) unqiue id
        val sid = mref.push().key ?: ""  // generate unique key for each scehduel process
        val selectedTimeStr = formattingTime(selected_time)
        val userId = currentUser.uid
        val scheduleData = Schedule(selectedDate,selectedTimeStr,sid)
        // Push the data under the current user's node in Firebase Database
        mref.child("Doctors_schedule").child(userId).child("schedules").child(sid).setValue(scheduleData)
            .addOnSuccessListener {
                Toast.makeText(this, "Schedule created successfully!  and ${selectedDate}", Toast.LENGTH_SHORT).show()

                val intent = Intent(this , Doctor_Home_Screen::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
             Toast.makeText(this, "Failed to create schedule: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
