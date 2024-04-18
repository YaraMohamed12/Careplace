package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LogandSignForPatient : AppCompatActivity() {
    lateinit var login_bt : Button
    lateinit var signup_bt : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_logand_sign_for_patient)
            login_bt = findViewById(R.id.login_btn)
            signup_bt = findViewById(R.id.signup_btn)


        login_bt.setOnClickListener {
            val myintent1 = Intent(this, LogInScreen::class.java)
            startActivity(myintent1)

        }

        signup_bt.setOnClickListener {
            val myintent2 = Intent(this , NewAccForPat::class.java)
            startActivity(myintent2)

        }

        }
    }
