package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class LogandSignForDoctor : AppCompatActivity() {
    lateinit var login_bt : Button
    lateinit var signup_bt : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logand_sign_for_doctor)
        login_bt = findViewById(R.id.login_btn2_doc)
        signup_bt = findViewById(R.id.signup_btn2_doc)

        login_bt.setOnClickListener {
            val myintent1 = Intent(this, LogInScreenForDoctor::class.java)
            startActivity(myintent1)

        }

        signup_bt.setOnClickListener {
            val myintent2 = Intent(this , NewAccForDoc::class.java)
            startActivity(myintent2)

        }

    }
}