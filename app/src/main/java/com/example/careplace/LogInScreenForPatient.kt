package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LogInScreenForPatient : AppCompatActivity() {
    lateinit var new_app: TextView
    lateinit var email_txt : TextInputEditText
    lateinit var passowrd_txt : TextInputEditText
    lateinit var login_btn : Button
    lateinit var forget_pass : TextView
    lateinit var myAuthn : FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_screen_for_patient)
        forget_pass = findViewById(R.id.restpassword)
        forget_pass.setOnClickListener {
            val myIntent5 = Intent(this,ForgetPasswordScreen::class.java)
            startActivity(myIntent5)
        }
       inilazaiton()
        LogIn_opt()
        newAccount()

        }
      fun inilazaiton()
      {
          new_app = findViewById(R.id.textView8)
          email_txt = findViewById(R.id.email_txt)
          passowrd_txt =findViewById(R.id.passowrd_txt)
          login_btn = findViewById(R.id.LogIn_btn)
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
//                        val myintent = Intent(this, Patient_Home_Screen::class.java)
//                        startActivity(myintent)
//                        finish()
                        EmailVerify()
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

    fun newAccount()
    {
        new_app.setOnClickListener {
            val myintent4 = Intent(this ,NewAccForPat::class.java)
            startActivity(myintent4)
        }
    }
    fun EmailVerify() {
        val user = myAuthn.currentUser
        if (user!!.isEmailVerified)
        {
        val myintent = Intent(this, Patient_Home_Screen::class.java)
            myintent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(myintent)
                        finish()
        }
        else
        {
            Toast.makeText(this, "PLEASE VERIFY YOUR EMAIL ACCOUNT", Toast.LENGTH_SHORT).show()
        }

    }


    }
