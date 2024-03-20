package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ListingDoctor : AppCompatActivity() {
 lateinit var toolbar: Toolbar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_doctor)
         toolbar = findViewById(R.id.mytoolbar)
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            if(menuItem.itemId == R.id.Log_out)
            {
                FirebaseAuth.getInstance().signOut()
                val myIntent = Intent(this,LogInScreen::class.java)
                startActivity(myIntent)
            }
            true
        }

    }

}

