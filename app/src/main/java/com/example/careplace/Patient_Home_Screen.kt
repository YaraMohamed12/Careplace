package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging

class Patient_Home_Screen : AppCompatActivity() {

    private lateinit var user_image: ImageView
    private lateinit var patname: TextView
    private lateinit var Goto_chat: ImageView
    private lateinit var select_doc: FrameLayout
    private lateinit var select_Mdeicine: FrameLayout
    private lateinit var home_btn: ImageView
    private lateinit var setting_btn: ImageView
    private lateinit var your_profile_btn: ImageView
    private lateinit var calender_btn: ImageView
    private lateinit var select_form: FrameLayout
    private lateinit var updimg_img: ImageView
    private lateinit var view: View
    private lateinit var database: FirebaseDatabase
    private lateinit var mref: DatabaseReference
    private lateinit var myathun: FirebaseAuth
    private var profileImageUrl: kotlin.String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_home_screen)

        // Initialize UI elements
        initViews()

        // Initialize Firebase
        myathun = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Import user data and load profile image
        importUserData()
        loadUserProfileImage()

        // Set click listeners
        setClickListeners()
        currentuserToken()

        // Read user data from Firebase
        val myreadata = ReadData()
        mref.addValueEventListener(myreadata)
    }

    private fun initViews() {
        patname = findViewById(R.id.textView14)
        Goto_chat = findViewById(R.id.goto_chat)
        select_doc = findViewById(R.id.choose_doctor)
        select_Mdeicine = findViewById(R.id.choose_medicine)
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        select_form = findViewById(R.id.choose_form)
        user_image = findViewById(R.id.select_image)
    }

    private fun importUserData() {
        val currentUser: FirebaseUser? = myathun.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            mref = database.getReference("user/$userId/name")
        }
    }

    private fun loadUserProfileImage() {
        val currentUser: FirebaseUser? = myathun.currentUser
        currentUser?.let { user ->
            val userRef = FirebaseDatabase.getInstance().getReference("user/${user.uid}/profileImageUrl")
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    profileImageUrl = snapshot.getValue(String::class.java) ?: ""
                    if (profileImageUrl.isNotEmpty()) {
                        Glide.with(this@Patient_Home_Screen).load(profileImageUrl).into(user_image)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Patient_Home_Screen, "Failed to read user profile image URL", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setClickListeners() {
        Goto_chat.setOnClickListener {
            startActivity(Intent(this, ContactActivity_For_Patient::class.java))
        }

        select_doc.setOnClickListener {
            startActivity(Intent(this, Choose_your_doc::class.java))
        }

        select_Mdeicine.setOnClickListener {
            startActivity(Intent(this, Medicine_List::class.java))
        }

        home_btn.setOnClickListener {
            // Not recommended to restart the same activity
            // startActivity(Intent(this, Patient_Home_Screen::class.java))
        }

        setting_btn.setOnClickListener {
            startActivity(Intent(this, Patient_Setting_Screen::class.java))
        }

        your_profile_btn.setOnClickListener {
            startActivity(Intent(this, My_Profile_Details_for_patient::class.java))
        }

        calender_btn.setOnClickListener {
            startActivity(Intent(this, Patient_Calender_Screen::class.java))
        }

        select_form.setOnClickListener {
            startActivity(Intent(this, Medical_History_For_Patient_View::class.java))
        }

        user_image.setOnClickListener {
            showImagePreviewDialog()
        }
    }

    private fun showImagePreviewDialog() {
        view = layoutInflater.inflate(R.layout.image_preview_dailog, null)
        val updimg_img = view.findViewById<ImageView>(R.id.Image_preview)
        Glide.with(this@Patient_Home_Screen).load(profileImageUrl).into(updimg_img)

        val myDialogBuilder = AlertDialog.Builder(this)
        myDialogBuilder.setView(view)
        val alertDialog = myDialogBuilder.create()
        alertDialog.show()
    }

    inner class ReadData : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val username = snapshot.getValue(String::class.java)
            patname.text = "Hi ${username ?: ""}"
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle onCancelled
        }
    }
    fun currentuserToken() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener { token ->
                    val userId = currentUser.uid
                    val userTokenRef = FirebaseDatabase.getInstance().getReference("user").child(userId).child("fcmToken")
                    userTokenRef.setValue(token)
                        .addOnSuccessListener {
                            // Token stored successfully
                            Toast.makeText(this, "Token stored successfully for user", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            // Failed to store token
                            Toast.makeText(this, "Failed to store token for user", Toast.LENGTH_SHORT).show()

                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "the error existist", Toast.LENGTH_SHORT).show()

                }
        }
    }


}




