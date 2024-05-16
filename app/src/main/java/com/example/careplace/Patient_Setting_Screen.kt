package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Patient_Setting_Screen : AppCompatActivity() {
    lateinit var home_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView
    lateinit var reset_btn : Button
    lateinit var log_out_btn : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_setting_screen)

        inilaztionlistenr()
        clicking()
        DeleteAccount()

    }

    private fun DeleteAccount() {
        val currentuser = FirebaseAuth.getInstance().currentUser
        if (currentuser != null) {
            val Delete_Account_Btn = findViewById<Button>(R.id.delet_acc)

            Delete_Account_Btn.setOnClickListener {
                currentuser.delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Account Deleted successfully", Toast.LENGTH_SHORT).show()
                     val mainintent = Intent(this, DocOrPat::class.java)
                     mainintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                     mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                     startActivity(mainintent)
                     finish()
                    }
                    .addOnFailureListener {
                     e->   Toast.makeText(this, "Failed to delete account : ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }

        }
    }

    private fun inilaztionlistenr() {
        home_btn = findViewById(R.id.home_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
        reset_btn = findViewById(R.id.reset_pass_btn)
        log_out_btn = findViewById(R.id.Log_out)
    }

    private fun clicking(){
        home_btn.setOnClickListener {
            val myintent1 = Intent(this , Patient_Home_Screen::class.java)
            startActivity(myintent1)
        }
        your_profile_btn.setOnClickListener {
            val myintent3 = Intent(this , My_Profile_Details_for_patient::class.java)
            startActivity(myintent3)
        }
        calender_btn.setOnClickListener {
            val myintent4 = Intent(this , Patient_Calender_Screen::class.java)
            startActivity(myintent4)
        }
        chat_btn.setOnClickListener {

            val myintent5 = Intent(this ,ContactActivity_For_Patient ::class.java)
            startActivity(myintent5)

        }
        log_out_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut() // Sign out the current user
            val myIntent = Intent(this, DocOrPat::class.java)
            // Add flags to clear the activity stack and prevent returning to the logout screen
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(myIntent)
            finish()
        }
    }
}