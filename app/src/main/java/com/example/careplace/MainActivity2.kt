package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
lateinit var Next_2 :Button
lateinit var Back_2 :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        Next_2 = findViewById(R.id.Next_2)
        Back_2 = findViewById(R.id.back_2)
        Next_2.setOnClickListener {
            val myIntent = Intent(this,MainActivity3:: class.java)
            startActivity(myIntent)
        }
        Back_2.setOnClickListener {
            val myIntent = Intent(this,MainActivity:: class.java)
            startActivity(myIntent)
        }

    }
}