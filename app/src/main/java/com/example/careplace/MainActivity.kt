package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

lateinit var Next_1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Next_1 = findViewById(R.id.Next_1)
        Next_1.setOnClickListener {
            val myIntent = Intent(this,MainActivity2:: class.java)
            startActivity(myIntent)
        }

    }

}