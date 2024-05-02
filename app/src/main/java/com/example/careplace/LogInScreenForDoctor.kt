package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LogInScreenForDoctor : AppCompatActivity() {
    lateinit var back_icon : ImageView
    lateinit var new_app: TextView
    lateinit var email_txt : TextInputEditText
    lateinit var passowrd_txt : TextInputEditText
    lateinit var login_btn : Button
    lateinit var forget_pass : TextView
    lateinit var myAuthn : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_screen_for_doctor)
        forget_pass = findViewById(R.id.restpassword_doc)
        forget_pass.setOnClickListener {
            val myIntent5 = Intent(this,ForgetPasswordScreen::class.java)
            startActivity(myIntent5)
        }
        inilazaiton()
        back()
        LogIn_opt()
        newAccount()
    }
    fun inilazaiton()
    {
        back_icon = findViewById(R.id.imageView_doc)
        new_app = findViewById(R.id.New_doc_Acc)
        email_txt = findViewById(R.id.email_txt_doc)
        passowrd_txt =findViewById(R.id.passowrd_txt_doc)
        login_btn = findViewById(R.id.LogIn_btn_doc)
        myAuthn = FirebaseAuth.getInstance()

    }
    fun LogIn_opt()
    {
        login_btn.setOnClickListener {
            val myemail = email_txt.text.toString()
            val mypasswoed = passowrd_txt.text.toString()
            myAuthn = FirebaseAuth.getInstance()
            if(myemail.isNotEmpty() && mypasswoed.isNotEmpty()) {
                myAuthn.signInWithEmailAndPassword(myemail, mypasswoed).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val myintent = Intent(this, Doctor_Home_Screen::class.java)
                        startActivity(myintent)
                        finish()
                    } else {
                        Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {
                Toast.makeText(this, "Enter Your Email And Password", Toast.LENGTH_SHORT).show()

            }

        }


    }
    fun back()
    {
        back_icon.setOnClickListener {
            val myintent3 = Intent(this , LogandSignForDoctor::class.java)
            startActivity(myintent3)
        }
    }
    fun newAccount()
    {
        new_app.setOnClickListener {
            val myintent4 = Intent(this ,NewAccForDoc::class.java)
            startActivity(myintent4)
        }
    }
}