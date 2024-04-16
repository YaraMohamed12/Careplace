package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.careplace.databinding.ActivityChooseYourDocBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Choose_your_doc : AppCompatActivity() {
    lateinit var select_spec1 : FrameLayout
    lateinit var binding: ActivityChooseYourDocBinding
    lateinit var dRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseYourDocBinding.inflate(layoutInflater)
        setContentView(binding.root)
        select_spec1 = findViewById(R.id.spec1_frame)

        select_spec1.setOnClickListener {
            dRef = FirebaseDatabase.getInstance().getReference("Cardiologist")
            val myIntent = Intent(this, Doctors_List::class.java)
            startActivity(myIntent)
        }
    }
}