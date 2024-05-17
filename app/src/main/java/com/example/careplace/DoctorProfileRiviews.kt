package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.String

class DoctorProfileRiviews : AppCompatActivity() {
    lateinit var info_btn: Button
    lateinit var review_btn: Button
    lateinit var home_btn: ImageView
    lateinit var setting_btn: ImageView
    lateinit var your_profile_btn: ImageView
    lateinit var calender_btn: ImageView
    lateinit var chat_btn: ImageView
    lateinit var adding_review: FloatingActionButton
    private lateinit var review_recycle: RecyclerView
    private lateinit var reviewList: ArrayList<Review>
    private lateinit var review_adabter: ReviewAdapter
    lateinit var mRef: DatabaseReference
    lateinit var Doctorid: String
    lateinit var doctor_name: TextView
    lateinit var Doctor_spec: TextView
    lateinit var doc_locat: TextView
    lateinit var rate_btn: Button
    lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile_riviews)
        initializeUI()

        Doctorid = intent.getStringExtra("DoctorId") ?: ""
        val doctorname = intent.getStringExtra("DoctorName") ?: ""
        val doctorspec = intent.getStringExtra("Doctor_spec") ?: ""
        val Doctor_loc = intent.getStringExtra("Doctor_loc") ?: "Unknown Location"

        doctor_name.text = doctorname
        Doctor_spec.text = doctorspec
        doc_locat.text = Doctor_loc

        adding_review.setOnClickListener { addInfo() }
        review_recycle.layoutManager = LinearLayoutManager(this)
        review_recycle.adapter = review_adabter

        setButtonListeners(doctorname, doctorspec)
        rateMyDoctor()
        readDoctorRating()
        loadReviews()
    }

    private fun initializeUI() {
        info_btn = findViewById(R.id.info_btn1)
        review_btn = findViewById(R.id.review_btn1)
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn = findViewById(R.id.goto_chat)
        adding_review = findViewById(R.id.adding_review_btn)
        review_recycle = findViewById(R.id.reviews_list)
        reviewList = ArrayList()
        review_adabter = ReviewAdapter(this, reviewList)
        mRef = FirebaseDatabase.getInstance().getReference()
        doctor_name = findViewById(R.id.textView201)
        Doctor_spec = findViewById(R.id.textView210)
        rate_btn = findViewById(R.id.Rate_btn)
        doc_locat = findViewById(R.id.Doctor_Review_loc)
        ratingBar = findViewById(R.id.ratingBar)
    }

    private fun setButtonListeners(doctorname: String, doctorspec: String) {
        info_btn.setOnClickListener {
            val myintent = Intent(this, DoctorProfileInfo::class.java)
            myintent.putExtra("DoctorId", Doctorid)
            myintent.putExtra("DoctorName", doctorname)
            myintent.putExtra("Doctor_spec", doctorspec)
            startActivity(myintent)
        }

        review_btn.setOnClickListener {
            val myintent = Intent(this, DoctorProfileRiviews::class.java)
            startActivity(myintent)
        }

        home_btn.setOnClickListener {
            val myintent1 = Intent(this, Patient_Home_Screen::class.java)
            startActivity(myintent1)
        }

        setting_btn.setOnClickListener {
            val myintent2 = Intent(this, Patient_Setting_Screen::class.java)
            startActivity(myintent2)
        }

        your_profile_btn.setOnClickListener {
            val myintent3 = Intent(this, My_Profile_Details_for_patient::class.java)
            startActivity(myintent3)
        }

        calender_btn.setOnClickListener {
            val myintent4 = Intent(this, Patient_Calender_Screen::class.java)
            startActivity(myintent4)
        }

        chat_btn.setOnClickListener {
            val myintent5 = Intent(this, ContactActivity_For_Patient::class.java)
            startActivity(myintent5)
        }
    }

    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_review_dialog, null)
        val reviewText = v.findViewById<EditText>(R.id.review_mess_d)
        val ratingValue = v.findViewById<RatingBar>(R.id.ratingBar3_review_d)
        val addReview = AlertDialog.Builder(this)
        addReview.setView(v)
        addReview.setPositiveButton("Ok") { dialog, _ ->
            val reviewContent = reviewText.text.toString()
            val rating = ratingValue.rating
            if (reviewContent.isNotEmpty()) {
                val review = Review(reviewContent, rating)
                val database = FirebaseDatabase.getInstance()
                val reviewsRef = database.getReference("doctor_reviews").child(Doctorid)
                val newReviewRef = reviewsRef.push()
                newReviewRef.setValue(review)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Review added successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to add review: ${e.message}", Toast.LENGTH_SHORT).show()
                    }

                reviewList.add(review)
                review_adabter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Review content cannot be empty", Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss()
        }
        addReview.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Review cancelled", Toast.LENGTH_SHORT).show()
        }
        addReview.create()
        addReview.show()
    }

    private fun loadReviews() {
        val database = FirebaseDatabase.getInstance()
        val reviewsRef = database.getReference("doctor_reviews").child(Doctorid)
        reviewsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                reviewList.clear()
                for (reviewSnapshot in snapshot.children) {
                    val review = reviewSnapshot.getValue(Review::class.java)
                    if (review != null) {
                        reviewList.add(review)
                    }
                }
                review_adabter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DoctorProfileRiviews, "Failed to load reviews: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun rateMyDoctor() {
        rate_btn.setOnClickListener {
            val ratingValue = ratingBar.rating

            if (Doctorid.isNotEmpty()) {
                val database = FirebaseDatabase.getInstance()
                val ratingsRef = database.getReference("doctor_ratings").child(Doctorid)
                ratingsRef.setValue(ratingValue)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Your rating is saved", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to save your rating: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Doctor ID is not provided", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readDoctorRating() {
        if (Doctorid.isNotEmpty()) {
            val database = FirebaseDatabase.getInstance()
            val ratingsRef = database.getReference("doctor_ratings").child(Doctorid)
            ratingsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val ratingValue = snapshot.getValue(Float::class.java)
                        if (ratingValue != null) {
                            ratingBar.rating = ratingValue
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@DoctorProfileRiviews, "Failed to retrieve rating", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Doctor ID is not provided", Toast.LENGTH_SHORT).show()
        }
    }
}
