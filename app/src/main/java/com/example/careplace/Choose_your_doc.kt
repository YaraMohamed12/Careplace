package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.careplace.databinding.ActivityChooseYourDocBinding

class Choose_your_doc : AppCompatActivity() {
    lateinit var select_spec1 : FrameLayout
    lateinit var binding: ActivityChooseYourDocBinding
    lateinit var mySpec : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseYourDocBinding.inflate(layoutInflater)
        setContentView(binding.root)
        select_spec1 = findViewById(R.id.spec1_frame)

        select_spec1.setOnClickListener {
            mySpec = "Cardiologist"
            val myIntent = Intent(this, Doctors_List::class.java)
            myIntent.putExtra("Spec",mySpec)
            startActivity(myIntent)
        }
        binding.spec2Frame.setOnClickListener {
            mySpec = "Pulmonologist"
            val myIntent = Intent(this, Doctors_List::class.java)
            myIntent.putExtra("Spec",mySpec)
            startActivity(myIntent)
        }

    }
}