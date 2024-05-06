package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Information_Form : AppCompatActivity() {
    lateinit var allergies :Button
    lateinit var medical_history :Button
    lateinit var surgeries :Button
    lateinit var x_ray_tests :Button
    lateinit var medication :Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_form)
        allergies = findViewById(R.id.allergy)
        medical_history  = findViewById(R.id.P_M_H)
        surgeries = findViewById(R.id.S)
        x_ray_tests = findViewById(R.id.Xray)
        medication = findViewById(R.id.M)

        allergies.setOnClickListener {
            val intent1 = Intent(this,Allergies::class.java)
            startActivity(intent1)
        }

        medical_history.setOnClickListener {
            val intent2 = Intent(this,Medical_History::class.java)
            startActivity(intent2)
        }

        surgeries.setOnClickListener {
            val intent3 = Intent(this,Surgeries::class.java)
            startActivity(intent3)
        }

        x_ray_tests.setOnClickListener {
            val intent4 = Intent(this,X_Rays_and_Medical_tests::class.java)
            startActivity(intent4)
        }

        medication.setOnClickListener {
            val intent5 = Intent(this,Medications::class.java)
            startActivity(intent5)
        }

    }
}