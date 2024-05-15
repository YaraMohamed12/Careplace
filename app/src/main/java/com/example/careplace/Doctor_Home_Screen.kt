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

class Doctor_Home_Screen : AppCompatActivity() {

    private lateinit var setting_btn: ImageView
    private lateinit var your_profile_btn: ImageView
    private lateinit var calender_btn: ImageView
    private lateinit var chat_btn: ImageView
    private lateinit var OpenResavtion: Button
    private lateinit var doctorName: TextView
    private lateinit var Pateint_btn: Button
    private lateinit var doctor_image: ImageView
    private lateinit var view: View
    private lateinit var updimg_img: ImageView
    private var profileImageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_home_screen)

        // Initialize UI elements
        initializeViews()

        // Set click listeners for buttons
        setButtonClickListeners()

        // Fetch and display doctor's name
        fetchDoctorName()

        // Load and display doctor's profile image
        loadDoctorProfileImage()
    }

    private fun initializeViews() {
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn = findViewById(R.id.goto_chat)
        OpenResavtion = findViewById(R.id.Open_schedule)
        doctorName = findViewById(R.id.Doctor_name)
        Pateint_btn = findViewById(R.id.my_patient_btn)
        doctor_image = findViewById(R.id.select_image_doctor)
    }

    private fun setButtonClickListeners() {
        setting_btn.setOnClickListener {
            startActivity(Intent(this, Doctor_Setting_Screen::class.java))
        }

        your_profile_btn.setOnClickListener {
            startActivity(Intent(this, My_Profile_Details_for_doc::class.java))
        }

        calender_btn.setOnClickListener {
            startActivity(Intent(this, Doctor_Calender_Screen::class.java))
        }

        chat_btn.setOnClickListener {
            startActivity(Intent(this, ContactActivity::class.java))
        }

        OpenResavtion.setOnClickListener {
            startActivity(Intent(this, Doctor_open_Schedule::class.java))
        }

        Pateint_btn.setOnClickListener {
            startActivity(Intent(this, Patient_List::class.java))
        }

        doctor_image.setOnClickListener {
            showImagePreviewDialog()
        }
    }

    private fun fetchDoctorName() {
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val userId: String? = currentUser?.uid
        if (userId != null) {
            val doctorRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("DUser").child(userId)
            doctorRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val doctorNameValue = snapshot.child("dname").getValue(String::class.java)
                    if (!doctorNameValue.isNullOrBlank()) {
                        doctorName.text = "Hi $doctorNameValue"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Doctor_Home_Screen, "Failed to fetch doctor's name", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun loadDoctorProfileImage() {
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val userRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("DUser").child(user.uid)
            userRef.child("profileImageUrl").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val imageUrl = snapshot.getValue(String::class.java)
                    if (!imageUrl.isNullOrEmpty()) {
                        profileImageUrl = imageUrl
                        Glide.with(this@Doctor_Home_Screen).load(profileImageUrl).into(doctor_image)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Doctor_Home_Screen, "Failed to read doctor's profile image URL", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showImagePreviewDialog() {
        view = layoutInflater.inflate(R.layout.image_preview_dailog, null)
        updimg_img = view.findViewById(R.id.Image_preview)
        Glide.with(this@Doctor_Home_Screen).load(profileImageUrl).into(updimg_img)

        val myDialogBuilder = AlertDialog.Builder(this)
        myDialogBuilder.setView(view)
        val alertDialog = myDialogBuilder.create()
        alertDialog.show()
    }
}
