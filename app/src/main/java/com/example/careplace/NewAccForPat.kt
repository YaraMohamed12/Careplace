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

    }

    private fun register() {
        sumbit.setOnClickListener {
             // edittextview -> variables -> dataclass -> send to store in database
            val mail = mailtxt.text.toString().trim()
            val password = passwordtxt.text.toString().trim()
            val fullname = binding.editxtname.text.toString().trim()
            val phone = binding.editxtphone.text.toString().trim()

            if (mail.isNotEmpty() && password.isNotEmpty() && fullname.isNotEmpty() && phone.isNotEmpty()) {
                if (!isValidName(fullname)) {
                    Toast.makeText(this, "Invalid name. Please use letters only.", Toast.LENGTH_SHORT).show()
                } else if (!isValidPhoneNumber(phone)) {
                    Toast.makeText(this, "Invalid phone number. Please enter 11 digits only.", Toast.LENGTH_SHORT).show()
                } else if (!isValidPassword(password)) {
                    Toast.makeText(this, "Invalid password. Please enter at least 8 characters with letters, numbers, and special characters.", Toast.LENGTH_SHORT).show()
                } else {
                    myAuthn1.createUserWithEmailAndPassword(mail, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userEmail = mail
                                val BDT = binding.editxtBDT.text.toString().trim()
                                val ID = binding.editxtID.text.toString().trim()
                                val gender = binding.editxtGender.text.toString().trim()
                                val age = binding.editxtAge.text.toString().trim()
                                val height = binding.editxtHeight.text.toString().trim()
                                val weight = binding.editxtWeight.text.toString().trim()

                                AddusertoDatabase(fullname, userEmail, myAuthn1.currentUser?.uid!!, phone, ID, height, weight, BDT, age, gender)
                            } else {
                                Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } else {
                Toast.makeText(this, "Please enter all required data.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun initializeViews() {
        mailtxt = binding.editxtemail
        passwordtxt = binding.editxtpassword
        sumbit = binding.sumbitBtn
        // vairables connect activity realtime & athuntaction
        database = FirebaseDatabase.getInstance()
        myAuthn1 = FirebaseAuth.getInstance()
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

    fun AddusertoDatabase(name: String, email: String, uid: String,
                          Phone : String, nationid : String, lenght : String,
                          wieght : String, Bdate : String, Age : String, Gender : String
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

    private fun isValidName(name: String): Boolean {

        val regex = "^[a-zA-Z\\s]+$".toRegex()
        return name.matches(regex)
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val regex = "^\\d{11}$".toRegex()
        return phoneNumber.matches(regex)
    }


    private fun isValidPassword(password: String): Boolean{
        val regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$".toRegex()
        return password.matches(regex)
    }


}