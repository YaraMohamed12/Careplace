package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

lateinit var Next_1 : Button
lateinit var Skip_2 : Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Next_1 = findViewById(R.id.Next_1)
        Next_1.setOnClickListener {
            val myIntent = Intent(this,MainActivity2:: class.java)
            startActivity(myIntent)
        }

        Skip_2 = findViewById(R.id.Skip_2)
        Skip_2.setOnClickListener {
            val myIntent1 = Intent(this,DocOrPat::class.java)
            startActivity(myIntent1)
        }

    }




}