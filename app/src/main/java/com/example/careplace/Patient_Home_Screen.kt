package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Patient_Home_Screen : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var user_image: ImageView
    private lateinit var patname: TextView
    private lateinit var database: FirebaseDatabase
    private lateinit var mref: DatabaseReference
    private lateinit var myathun: FirebaseAuth
    private var Appuser: FirebaseUser? = null
    lateinit var Goto_chat : ImageView
    lateinit var select_doc : FrameLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_home_screen)
        ImportUserData()
        user_image = findViewById(R.id.select_image)
        patname = findViewById(R.id.textView13)
        Goto_chat = findViewById(R.id.goto_chat)
        select_doc = findViewById(R.id.choose_doctor)
        toolbar = findViewById(R.id.mytoolbar)
        toolbar.inflateMenu(R.menu.main_menu)
        menuInflate()
        val myreadata = ReadData()
        mref.addValueEventListener(myreadata)
        Goto_chat.setOnClickListener {
            val intent = Intent(this,ContactActivity::class.java)
            startActivity(intent)
        }

        select_doc.setOnClickListener {
            val my_intent = Intent(this,Choose_your_doc::class.java)
            startActivity(my_intent)
        }

    }

    private fun menuInflate() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.Log_out) {
                FirebaseAuth.getInstance().signOut()
                val myIntent = Intent(this@Patient_Home_Screen, LogInScreen::class.java)
                startActivity(myIntent)
                true
            } else {
                false
            }
        }
    }

    private fun ImportUserData() {
        database = FirebaseDatabase.getInstance()
        myathun = FirebaseAuth.getInstance()
        Appuser = myathun.currentUser
        if (Appuser != null)  {
            mref = database.getReference("user/${myathun.currentUser!!.uid}/name")


        }
    }

    inner class ReadData : ValueEventListener {
        // export userdata from database
        override fun onDataChange(snapshot: DataSnapshot) {
            val username = snapshot.getValue(String::class.java)
            patname.text = username
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle onCancelled
        }
    }
}
