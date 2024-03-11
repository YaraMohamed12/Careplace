package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {
    lateinit var Next_3 : Button
    lateinit var Back_3 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        Next_3 = findViewById(R.id.Next_3)
        Back_3 = findViewById(R.id.back_3)
        Next_3.setOnClickListener {
            val myIntent = Intent(this,MainActivity4:: class.java)
            startActivity(myIntent)
        }
        Back_3.setOnClickListener {
            val myIntent = Intent(this,MainActivity2:: class.java)
            startActivity(myIntent)
        }

    }
}