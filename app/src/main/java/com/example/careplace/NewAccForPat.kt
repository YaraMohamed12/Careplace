package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class NewAccForPat : AppCompatActivity() {
    lateinit var mailtxt : TextInputEditText
    lateinit var passwordtxt : TextInputEditText
    lateinit var myAuthn1 : FirebaseAuth
    lateinit var sumbit : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_acc_screen)
        Initialzation()
       Resgister()

    }
    fun Resgister()
    {
        sumbit.setOnClickListener {
            val password = passwordtxt.text.toString()
            val mail = mailtxt.text.toString()
            if(mail.isNotEmpty() && password.isNotEmpty()) {
                myAuthn1.createUserWithEmailAndPassword(mail, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Create Successful", Toast.LENGTH_SHORT).show()
                        SendMailVerifaction()

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {
                Toast.makeText(this, "You should enter your mail and password", Toast.LENGTH_SHORT).show()
            }



        }

    }
    fun Initialzation()
    {

        mailtxt = findViewById(R.id.editxtemail)
        passwordtxt = findViewById(R.id.editxtpassword)
        myAuthn1 = FirebaseAuth.getInstance()
        sumbit = findViewById(R.id.sumbitBtn)


    }
@SuppressLint("SuspiciousIndentation")
fun SendMailVerifaction()
{
 val user = myAuthn1.currentUser
    user?.sendEmailVerification()?.addOnCompleteListener {
        if (it.isSuccessful) {
            val myintent = Intent(this, LogInScreen::class.java)
            startActivity(myintent)
        }
        else{
            Toast.makeText(this, "Verfiaction Mail Is Not Send", Toast.LENGTH_SHORT).show()
        }

    }


}

}