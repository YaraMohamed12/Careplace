package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DoctorProfileInfo : AppCompatActivity() {
    lateinit var review_btn: Button
    lateinit var home_btn: ImageView
    lateinit var setting_btn: ImageView
    lateinit var your_profile_btn: ImageView
    lateinit var calender_btn: ImageView
    lateinit var Doctorid: kotlin.String
    lateinit var doctor_name: TextView
    lateinit var Doctor_spec: TextView
    lateinit var chat_btn: ImageView
    lateinit var rate_btn: Button
    lateinit var about_doctor: TextView
    lateinit var doc_locat: TextView
    lateinit var ratingBar: RatingBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile_info)

        // Initialize UI components
        initializeUI()

        // Retrieve intent data
        Doctorid = intent.getStringExtra("DoctorId") ?: ""
        val doctorname = intent.getStringExtra("DoctorName") ?: ""
        val doctorspec = intent.getStringExtra("Doctor_spec") ?: ""
        val Doctor_loc = intent.getStringExtra("Doctor_loc") ?: "Unknown Location"

        // Set text views with intent data
        doctor_name.text = doctorname
        Doctor_spec.text = doctorspec
        doc_locat.text = Doctor_loc

        // Ensure DoctorId is not empty before proceeding
        if (Doctorid.isNotEmpty()) {
            // Set up event listeners
            setRateButtonListener()
            readAboutMe()
            readDoctorRating()
        } else {
            Toast.makeText(this, "Doctor ID is not provided", Toast.LENGTH_SHORT).show()
        }

        // Set click listeners for other buttons
        setClickListeners()

        review_btn.setOnClickListener {
            val myintent = Intent(this, DoctorProfileRiviews::class.java)
            myintent.putExtra("DoctorId", Doctorid)
            myintent.putExtra("DoctorName", doctorname)
            myintent.putExtra("Doctor_spec", doctorspec)
            myintent.putExtra("Doctor_loc", Doctor_loc)
            startActivity(myintent)
        }
    }

    private fun initializeUI() {
        review_btn = findViewById(R.id.review_btn1)
        doctor_name = findViewById(R.id.textView20)
        Doctor_spec = findViewById(R.id.textView21)
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn = findViewById(R.id.goto_chat)
        rate_btn = findViewById(R.id.Rate_btn)
        about_doctor = findViewById(R.id.about_doc)
        doc_locat = findViewById(R.id.Doctor_info_loc)
        ratingBar = findViewById(R.id.ratingBar)
    }

    private fun setClickListeners() {
        home_btn.setOnClickListener {
            val myintent1 = Intent(this, Patient_Home_Screen::class.java)
            startActivity(myintent1)
        }

        setting_btn.setOnClickListener {
            val myintent2 = Intent(this, Patient_Setting_Screen::class.java)
            startActivity(myintent2)
        }

        your_profile_btn.setOnClickListener {
            val myintent3 = Intent(this, My_Profile_Details_for_patient::class.java)
            startActivity(myintent3)
        }

        calender_btn.setOnClickListener {
            val myintent4 = Intent(this, Patient_Calender_Screen::class.java)
            startActivity(myintent4)
        }

        chat_btn.setOnClickListener {
            val myintent5 = Intent(this, ContactActivity_For_Patient::class.java)
            startActivity(myintent5)
        }
    }

    private fun setRateButtonListener() {
        rate_btn.setOnClickListener {
            val ratingValue = ratingBar.rating

            if (Doctorid.isNotEmpty()) {
                val database = FirebaseDatabase.getInstance()
                val ratingsRef = database.getReference("doctor_ratings").child(Doctorid)
                ratingsRef.setValue(ratingValue)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Your rate is saved", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Your rate failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Doctor ID is not provided", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readAboutMe() {
        if (Doctorid.isNotEmpty()) {
            val database = FirebaseDatabase.getInstance()
            val aboutMeRef = database.getReference("DUser").child(Doctorid).child("aboutMe")
            aboutMeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val aboutMe = snapshot.getValue(String::class.java)
                    about_doctor.text = aboutMe
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@DoctorProfileInfo, "Failed to retrieve user info", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Doctor ID is not provided", Toast.LENGTH_SHORT).show()
        }
    }

    private fun readDoctorRating() {
        if (Doctorid.isNotEmpty()) {
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
                    Toast.makeText(this@DoctorProfileInfo, "Failed to retrieve rating", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Doctor ID is not provided", Toast.LENGTH_SHORT).show()
        }
    }
}

