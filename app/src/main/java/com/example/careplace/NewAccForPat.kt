package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.careplace.databinding.ActivityNewAccForPatBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NewAccForPat : AppCompatActivity() {

    private lateinit var mailtxt: TextInputEditText
    private lateinit var passwordtxt: TextInputEditText
    private lateinit var sumbit: Button
    private lateinit var back_btn: ImageView
    private lateinit var binding: ActivityNewAccForPatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myAuthn1: FirebaseAuth
    private lateinit var mRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAccForPatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeViews()
        register()
        backbutton()
    }

    private fun register() {
        sumbit.setOnClickListener {
            val mail = mailtxt.text.toString()
            val password = passwordtxt.text.toString()
            if (mail.isNotEmpty() && password.isNotEmpty()) {
                myAuthn1.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                                val userEmail = mail
                                val fullname = binding.editxtname.text.toString()
                                val phone = binding.editxtphone.text.toString()
                                val BDT = binding.editxtBDT.text.toString()
                                val ID = binding.editxtID.text.toString()
                                val gender = binding.editxtGender.text.toString()
                                val age = binding.editxtAge.text.toString()
                                val height = binding.editxtHeight.text.toString()
                                val weight = binding.editxtWeight.text.toString()
                               AddusertoDatabase(fullname,userEmail,myAuthn1.currentUser?.uid!!,phone,ID,height,weight,BDT,age,gender )

                        } else {
                            Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, " please Enter your Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initializeViews() {
        mailtxt = binding.editxtemail
        passwordtxt = binding.editxtpassword
        sumbit = binding.sumbitBtn
        back_btn = binding.imageView2
        database = FirebaseDatabase.getInstance()
        myAuthn1 = FirebaseAuth.getInstance()
    }

    private fun backbutton() {
        back_btn.setOnClickListener {
            val myIntent3 = Intent(this, LogandSignForPatient::class.java)
            startActivity(myIntent3)
        }
    }

    private fun SendMailVerification() {
        val user = myAuthn1.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val myintent = Intent(this, LogInScreenForPatient::class.java)
                startActivity(myintent)
            } else {
                Toast.makeText(this, "Verification Mail Is Not Sent: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun AddusertoDatabase(name: kotlin.String, email: kotlin.String, uid: kotlin.String,
                          Phone : kotlin.String, nationid : kotlin.String, lenght : kotlin.String,
                          wieght : kotlin.String, Bdate : kotlin.String, Age : kotlin.String, Gender : kotlin.String
    )
    {

        mRef = FirebaseDatabase.getInstance().getReference()
        mRef.child("user").child(uid)
            .setValue(Users(name, email, uid, Phone, nationid, lenght, wieght, Bdate, Age, Gender)).addOnSuccessListener {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                SendMailVerification()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Registration Failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }


    }


}