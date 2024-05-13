package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Patient_Detail : AppCompatActivity() {
    lateinit var Pateint_image : ImageView
    lateinit var Patient_name : TextView
    lateinit var Patient_gender: TextView
    lateinit var Patient_age : TextView
    lateinit var Patient_weight : TextView
    lateinit var Patient_height : TextView
    lateinit var Patient_phone : TextView
    lateinit var Patient_Forms : Button
    lateinit var mRef : DatabaseReference
    lateinit var PateintId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_detail)
        inilaztion()
        SetupListner()
        PateintId = intent.getStringExtra("PateintId")!!
        RetrivePatient()



    }


    fun inilaztion ()
    {
        Patient_name = findViewById(R.id.Selected_Patient_Name)
        Pateint_image = findViewById(R.id.select_image_patient)
        Patient_Forms = findViewById(R.id.Medical_form_patient)
        Patient_gender = findViewById(R.id.p_type)
        Patient_phone = findViewById(R.id.p_phone)
        Patient_weight = findViewById(R.id.p_weight)
        Patient_height = findViewById(R.id.p_height)
        Patient_age = findViewById(R.id.p_birthday)
        mRef = FirebaseDatabase.getInstance().getReference("user")
    }
    @SuppressLint("SuspiciousIndentation")
    fun SetupListner(){
        Patient_Forms.setOnClickListener {
            val intent = Intent(this,Medical_History_For_Doctor_View::class.java)
            intent.putExtra("PateintId",PateintId)
            startActivity(intent)
        }

    }
    private fun RetrivePatient() {
        mRef.child(PateintId).addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val P_name = snapshot.child("name").getValue(String ::class.java)
                val P_age = snapshot.child("bdate").getValue(String ::class.java)
                val P_weight = snapshot.child("wieght").getValue(String ::class.java)
                val P_height= snapshot.child("lenght").getValue(String ::class.java)
                val P_phone = snapshot.child("phone").getValue(String ::class.java)
                val P_gender = snapshot.child("gender").getValue(String ::class.java)
                Patient_name.text = P_name
                Patient_height.text = "Patient Height:$P_height"
                Patient_weight.text = "Patient Weight:$P_weight"
                Patient_phone.text = "Patient Phone:$P_phone"
                Patient_gender.text = "Patient Gender:$P_gender"
                Patient_age.text = "Patient Birth Date:$P_age"
                val profileImageUrl = snapshot.child("profileImageUrl").getValue(String::class.java)
                Glide.with(this@Patient_Detail).load(profileImageUrl).into(Pateint_image)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
