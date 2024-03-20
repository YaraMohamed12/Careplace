package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewAccForDoc : AppCompatActivity() {
    lateinit var back_btn : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_acc_for_doc)

        back_btn.setOnClickListener {
            val myIntent3 = Intent(this ,DocOrPat::class.java)
            startActivity(myIntent3)
        }
    }
}