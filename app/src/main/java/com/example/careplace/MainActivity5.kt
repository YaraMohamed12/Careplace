package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity5 : AppCompatActivity() {
    lateinit var Next_5 : Button
    lateinit var Back_5 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main5)
        Next_5 = findViewById(R.id.Next_5)
        Back_5 = findViewById(R.id.back_5)
        Next_5.setOnClickListener {
            val myIntent = Intent(this,MainActivity:: class.java)
            startActivity(myIntent)
        }
        Back_5.setOnClickListener {
            val myIntent = Intent(this,MainActivity4:: class.java)
            startActivity(myIntent)
        }

    }
}