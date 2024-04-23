package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class LogandSignForDoctor : AppCompatActivity() {
    lateinit var login_bt : Button
    lateinit var signup_bt : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_logand_sign_for_doctor)
        login_bt = findViewById(R.id.login_btn2)
        signup_bt = findViewById(R.id.signup_btn2)

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