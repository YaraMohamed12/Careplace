package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Doctor_Home_Screen : AppCompatActivity() {
    lateinit var setting_btn: ImageView
    lateinit var your_profile_btn: ImageView
    lateinit var calender_btn: ImageView
    lateinit var chat_btn: ImageView
    lateinit var OpenResavtion : Button
    lateinit var doctorName : TextView
    lateinit var Pateint_btn : Button
    lateinit var doctor_image : ImageView
    lateinit var view : View
    lateinit var updimg_img : ImageView
     var profileImageUrl : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_home_screen)
        inilaztionval()
        buttonlistener()
        fetchDoctorName()
        imageReview()
        RloadDocImage()


    }
    fun inilaztionval()
    {

        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn = findViewById(R.id.goto_chat)
        OpenResavtion = findViewById(R.id.Open_schedule)
        doctorName =findViewById(R.id.Doctor_name)
        Pateint_btn = findViewById(R.id.my_patient_btn)
        doctor_image = findViewById(R.id.select_image_doctor)
    }

    fun buttonlistener()
    {
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
        your_profile_btn.setOnClickListener {
            val myintent = Intent(this , My_Profile_Details_for_doc::class.java)
            startActivity(myintent)
        }


    }
    private fun fetchDoctorName() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid
        val doctorRef = FirebaseDatabase.getInstance().getReference("DUser")
        doctorRef.child(userId!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val doctorNameValue = snapshot.child("dname").value as? String
                if (!doctorNameValue.isNullOrBlank()) {
                    doctorName.text = "Hi $doctorNameValue"
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Doctor_Home_Screen, "Failed to fetch doctor's name", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun imageReview() {
        doctor_image.setOnClickListener {
            view = layoutInflater.inflate(R.layout.image_preview_dailog, null)
            val myDialogBuilder = AlertDialog.Builder(this)
            myDialogBuilder.setView(view)
            val alertDialog = myDialogBuilder.create()
            alertDialog.show()
            updimg_img = view.findViewById(R.id.Image_preview)
            Glide.with(this@Doctor_Home_Screen).load(profileImageUrl).into(updimg_img)
        }
    }

    fun RloadDocImage()
    {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val userRef = FirebaseDatabase.getInstance().getReference("DUser/${user.uid}")
            userRef.child("profileImageUrl").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    profileImageUrl = snapshot.getValue(String::class.java)!!
                    if (!profileImageUrl.isNullOrEmpty()) {
                        Glide.with(this@Doctor_Home_Screen).load(profileImageUrl).into(doctor_image)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Doctor_Home_Screen, "Failed to read user profile image URL", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}