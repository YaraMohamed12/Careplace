package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DocOrPat : AppCompatActivity() {
    lateinit var pateint_btn : Button
    lateinit var doctor_btn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_or_pat)
        pateint_btn = findViewById(R.id.Pateint_btn)
        doctor_btn = findViewById(R.id.Doctor_btn)
        pateint_btn.setOnClickListener {

            val myintent = Intent(this , LogandSignForPatient ::class.java)
            startActivity(myintent)

        }
        doctor_btn.setOnClickListener {

            val myintent2 = Intent(this,LogandSignForDoctor::class.java)
            startActivity(myintent2)

        }


    }
}