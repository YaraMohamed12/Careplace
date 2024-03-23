package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Patient_Home_Screen : AppCompatActivity() {
 lateinit var toolbar: Toolbar
 lateinit var user_image : ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_home_screen)

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

        user_image = findViewById(R.id.select_image)
        user_image.setOnClickListener {
            val myintent3 = Intent(Intent.ACTION_PICK)
            myintent3.type = "image/*"
            try {
                startActivityForResult(myintent3, 5)
            } catch (e: Exception) {
                // Handle exception, if any
                e.printStackTrace()
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5 && resultCode == RESULT_OK) {
            // Handle selected image here
        }
    }
}



