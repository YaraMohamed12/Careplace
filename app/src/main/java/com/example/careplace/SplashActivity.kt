package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Using Handler to delay the screen transition
        Handler(Looper.getMainLooper()).postDelayed({
            userCheck()
        }, SPLASH_TIME_OUT)
    }

    private fun userCheck() {
        val mAuth = FirebaseAuth.getInstance()
        val mref = FirebaseDatabase.getInstance().getReference("user")
        val currentUser = mAuth.currentUser

        if (currentUser != null) {
            mref.child(currentUser.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        startActivity(Intent(this@SplashActivity,
                            Patient_Home_Screen::class.java))
                    } else {

                        startActivity(Intent(this@SplashActivity,
                            Doctor_Home_Screen::class.java))
                    }
                    finish()
                }

                override fun onCancelled(error: DatabaseError) {

                    Toast.makeText(this@SplashActivity, "Database error occurred",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SplashActivity,
                        MainActivity::class.java))
                    finish()
                }
            })
        } else {

            startActivity(Intent(this@SplashActivity,
                MainActivity::class.java))
                  finish()
        }
    }
}
