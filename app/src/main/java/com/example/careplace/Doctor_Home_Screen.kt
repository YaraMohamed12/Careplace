package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Doctor_Home_Screen : AppCompatActivity() {
    lateinit var home_btn: ImageView
    lateinit var setting_btn: ImageView
    lateinit var your_profile_btn: ImageView
    lateinit var calender_btn: ImageView
    lateinit var chat_btn: ImageView
    lateinit var OpenResavtion : Button
    lateinit var doctorName : TextView
    lateinit var Pateint_btn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_home_screen)
        inilaztionval()
        buttonlistener()
        fetchDoctorName()
    }
    fun inilaztionval()
    {
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn = findViewById(R.id.goto_chat)
        OpenResavtion = findViewById(R.id.Open_schedule)
        doctorName =findViewById(R.id.Doctor_name)
        Pateint_btn = findViewById(R.id.my_patient_btn)
    }

    fun buttonlistener()
    {
        home_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val myIntent = Intent(this@Doctor_Home_Screen, DocOrPat::class.java)
            startActivity(myIntent)
            finish()

        }

        setting_btn.setOnClickListener {

            val myintent2 = Intent(this, Doctor_Setting_Screen::class.java)
            startActivity(myintent2)

        }

        chat_btn.setOnClickListener {

            val myintent5 = Intent(this, ContactActivity::class.java)
            startActivity(myintent5)

        }
     OpenResavtion.setOnClickListener {
         val myintent = Intent(this , Doctor_open_Schedule::class.java)
         startActivity(myintent)
     }
        calender_btn.setOnClickListener {

            val myintent = Intent(this , Doctor_Calender_Screen::class.java)
            startActivity(myintent)
        }
        Pateint_btn.setOnClickListener {
            val myintent = Intent(this , Patient_List::class.java)
            startActivity(myintent)
        }


    }
    private fun fetchDoctorName() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid ?: return


        val doctorRef = FirebaseDatabase.getInstance().getReference(userId)

        doctorRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val doctorNameValue = snapshot.child("dname").value as? String
                if (!doctorNameValue.isNullOrBlank()) {
                    doctorName.text = doctorNameValue
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Doctor_Home_Screen, "Failed to fetch doctor's name", Toast.LENGTH_SHORT).show()
            }
        })
    }

}