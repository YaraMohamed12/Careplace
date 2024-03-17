package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LogandSign : AppCompatActivity() {
    lateinit var login_bt : Button
    lateinit var signup_bt : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_logand_sign)
            login_bt = findViewById(R.id.login_btn)
            signup_bt = findViewById(R.id.signup_btn)


        login_bt.setOnClickListener {
            val myintent1 = Intent(this, LogInScreen::class.java)
            startActivity(myintent1)

        }

        signup_bt.setOnClickListener {
            val myintent2 = Intent(this , NewAccScreen::class.java)
            startActivity(myintent2)

        }

        }
    }
