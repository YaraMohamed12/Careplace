package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity4 : AppCompatActivity() {
    lateinit var Next_4 : Button
    lateinit var Back_4 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main4)
        Next_4 = findViewById(R.id.Next_4)
        Back_4 = findViewById(R.id.back_4)
        Next_4.setOnClickListener {
            val myIntent = Intent(this,MainActivity5:: class.java)
            startActivity(myIntent)
        }
        Back_4.setOnClickListener {
            val myIntent = Intent(this,MainActivity3:: class.java)
            startActivity(myIntent)
        }

    }
}