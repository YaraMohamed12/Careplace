package com.example.careplace

import android.annotation.SuppressLint
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LogInScreen : AppCompatActivity() {
    lateinit var back_icon : ImageView
    lateinit var new_app: TextView
    lateinit var email_txt : TextInputEditText
    lateinit var passowrd_txt : TextInputEditText
    lateinit var login_btn : Button
    lateinit var myAuthn : FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in_screen)
       inilazaiton()
        back()
        LogIn_opt()
        newAccount()

        }
      fun inilazaiton()
      {
          back_icon = findViewById(R.id.imageView2)
          new_app = findViewById(R.id.textView8)
          email_txt = findViewById(R.id.email_txt)
          passowrd_txt =findViewById(R.id.passowrd_txt)
          login_btn = findViewById(R.id.LogIn_btn)
          myAuthn = FirebaseAuth.getInstance()

      }
    fun LogIn_opt()
    {
//        login_btn.setOnClickListener {
//            val myemail = email_txt.text.toString()
//            val mypassword = passowrd_txt.text.toString()
//            if(myemail.isNotEmpty() && mypassword.isNotEmpty()) {
//                myAuthn.createUserWithEmailAndPassword(myemail, mypassword).addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        Toast.makeText(this, "Create Successful", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//            else
//            {
//                Toast.makeText(this, "You should enter your mail and password", Toast.LENGTH_SHORT).show()
//            }
//
//        }

        login_btn.setOnClickListener {
            val myemail = email_txt.text.toString()
            val mypasswoed = passowrd_txt.text.toString()
            myAuthn = FirebaseAuth.getInstance()
            if(myemail.isNotEmpty() && mypasswoed.isNotEmpty()) {
                myAuthn.signInWithEmailAndPassword(myemail, mypasswoed).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val myintent = Intent(this, ListingDoctor::class.java)
                        startActivity(myintent)
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
            val myintent3 = Intent(this , LogandSign::class.java)
            startActivity(myintent3)
        }
    }
    fun newAccount()
    {
        new_app.setOnClickListener {
            val myintent4 = Intent(this , NewAccScreen::class.java)
            startActivity(myintent4)
        }
    }


    }