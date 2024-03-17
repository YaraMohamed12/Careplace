package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.careplace.R.id.mytoolbar
import com.google.firebase.auth.FirebaseAuth

class ListingDoctor : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_doctor)
        toolbar = findViewById(mytoolbar)
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

