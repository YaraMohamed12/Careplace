package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class Doctor_open_Schedule : AppCompatActivity() {
    private lateinit var selected_date: CalendarView
    private lateinit var selected_time: TimePicker
    private lateinit var sumbit_btn: Button
    private lateinit var mref: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var selectedDate : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_open_schedule)
        initialization()
        selected_date.setOnDateChangeListener { view, year, month, dayOfMonth ->
             selectedDate = formatDate(year, month, dayOfMonth)
            Toast.makeText(this, "Selected date: $selectedDate", Toast.LENGTH_SHORT).show()
        }

        sumbit_btn.setOnClickListener {
            submitData()
        }

    }

    //  data from xml view picker / caledner
    // valrable have same datatype pass value from view
    // two val pass fns formatting current type to string
    // store in two val store datbase

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
