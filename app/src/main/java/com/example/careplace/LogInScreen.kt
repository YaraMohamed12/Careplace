package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LogInScreen : AppCompatActivity() {
    lateinit var back_icon : ImageView
    lateinit var new_app: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in_screen)
        back_icon = findViewById(R.id.imageView2)
        new_app = findViewById(R.id.textView8)

        back_icon.setOnClickListener {
            val myintent3 = Intent(this , LogandSign::class.java)
            startActivity(myintent3)
        }

        new_app.setOnClickListener {
            val myintent4 = Intent(this , NewAccScreen::class.java)
            startActivity(myintent4)
        }

        }
    }
