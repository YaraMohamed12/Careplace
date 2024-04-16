package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Choose_your_doc : AppCompatActivity() {
    lateinit var select_spec1 : FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_your_doc)
        select_spec1 = findViewById(R.id.spec1_frame)

        select_spec1.setOnClickListener {
            val myIntent = Intent(this, Doctors_List::class.java)
            startActivity(myIntent)
        }
    }
}