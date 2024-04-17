package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.careplace.databinding.ActivityNewAccForDocBinding
import com.example.careplace.databinding.ActivityNewAccForPatBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NewAccForDoc : AppCompatActivity() {
    lateinit var back_btn : ImageView
    lateinit var toDropDownMenu : AutoCompleteTextView
    val spec1 : String = "Cardiologist"
    val spec2 : String = "Pulmonologist"
    val spec3 : String = "orthopaedic"
    val spec4 : String = "Ophthalmologist"
    val spec5 : String = "Dentist"
    val spec6 : String = "Audiologist"
    val spec7 : String = "ENT Specialists"
    private lateinit var Dmailtxt: TextInputEditText
    private lateinit var Dpasswordtxt: TextInputEditText
    private lateinit var sumbit: Button
    private lateinit var binding: ActivityNewAccForDocBinding
    private lateinit var Ddatabase: FirebaseDatabase
    private lateinit var DmyAuthn1: FirebaseAuth
    private lateinit var DmRef : DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAccForDocBinding.inflate(layoutInflater)
        setContentView(binding.root)
        back_btn =findViewById(R.id.backbtndoc)
        toDropDownMenu = findViewById(R.id.editxtSpecialization)
         initializeViews()
        menupopulater()
        backbutton()
        register()
    }
    private fun menupopulater()
    {

        val listDoctor = listOf(spec1,spec2,spec3,spec4,spec5,spec6,spec7)
        val adapter = ArrayAdapter(this,R.layout.drop_down_menu,listDoctor)
        toDropDownMenu.setAdapter(adapter)


    }
    private fun backbutton()
    {
        back_btn.setOnClickListener {
            val myIntent3 = Intent(this ,DocOrPat::class.java)
            startActivity(myIntent3)
        }

    }
    private fun register() {
        sumbit.setOnClickListener {
            val mail = Dmailtxt.text.toString()
            val password = Dpasswordtxt.text.toString()
            if (mail.isNotEmpty() && password.isNotEmpty()) {
                DmyAuthn1.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userEmail = mail
                            val fullname = binding.editxtname2.text.toString()
                            val phone = binding.editxtphone2.text.toString()
                            val BDT = binding.editxtBDT2.text.toString()
                            val ID = binding.editxtID2.text.toString()
                            val gender = binding.editxtGender2.text.toString()
                            val age = binding.editxtAge2.text.toString()
                            val speacilazation = binding.editxtSpecialization.text.toString()
                            AddusertoDatabase(fullname,userEmail,DmyAuthn1.currentUser?.uid!!,phone,ID,BDT,age,gender,speacilazation)

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
        Dmailtxt = binding.editxtemail2
        Dpasswordtxt = binding.editxtpassword2
        sumbit = binding.sumbitBtn
        Ddatabase = FirebaseDatabase.getInstance()
        DmyAuthn1 = FirebaseAuth.getInstance()
    }



    private fun SendMailVerification() {
        val user = DmyAuthn1.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val myintent = Intent(this, LogInScreen::class.java)
                startActivity(myintent)
            } else {
                Toast.makeText(this, "Verification Mail Is Not Sent: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun AddusertoDatabase(name: String, email: String, uid: String ,
                          Phone :String, nationid : String ,
                           Bdate : String, Age : String, Gender : String ,Spec : String)
    {

        DmRef = FirebaseDatabase.getInstance().getReference()
        DmRef.child(Spec).child("Duser").child(uid)
            .setValue(DUsers(name, email, uid, Phone, nationid, Bdate, Age, Gender,Spec)).addOnSuccessListener {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                SendMailVerification()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Registration Failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }


    }
}