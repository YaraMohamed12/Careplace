package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordScreen : AppCompatActivity() {
    lateinit var email_Reset: TextInputEditText
    lateinit var rest_btn: Button
    lateinit var creatacc_txt: TextView
    lateinit var Blogin_btn: Button
    lateinit var myAuth: FirebaseAuth
    lateinit var Notifacty_btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_screen)
        Iniliaztion()
        GotoLOgIN()
        GotoCreatAcc()
        rest_btn.setOnClickListener {
            Resetpass()
        }
    }

    fun Iniliaztion() {
        email_Reset = findViewById(R.id.emailreset)
        rest_btn = findViewById(R.id.Reset_btn)
        Blogin_btn = findViewById(R.id.backLog_btn)
        creatacc_txt = findViewById(R.id.creatmailtxt)
        myAuth = FirebaseAuth.getInstance()

    }

    fun GotoLOgIN() {
        Blogin_btn.setOnClickListener {
            val myintent = Intent(this, DocOrPat::class.java)
            startActivity(myintent)
        }

    }

    fun GotoCreatAcc() {
        creatacc_txt.setOnClickListener {
            val myintent = Intent(this, DocOrPat::class.java)
            startActivity(myintent)
        }
    }
    fun Resetpass() {
        val remail = email_Reset.text.toString()
        if (remail.isNotEmpty()) {
            myAuth.sendPasswordResetEmail(remail).addOnSuccessListener {
                Toast.makeText(this, "P+lease Check Your Email", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
        }
    }
}
