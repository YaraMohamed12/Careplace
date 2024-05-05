package com.example.careplace

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DoctorProfileRiviews : AppCompatActivity() {
    lateinit var schedule_btn : Button
    lateinit var info_btn : Button
    lateinit var review_btn : Button
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView
    lateinit var adding_review : FloatingActionButton
    private lateinit var review_recycle :RecyclerView
    private lateinit var reviewList: ArrayList<Review>
    private lateinit var review_adabter : ReviewAdapter
    lateinit var mRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile_riviews)
        Iniliaztion()

        adding_review.setOnClickListener{ addInfo() }
        review_recycle.layoutManager = LinearLayoutManager(this)
        review_recycle.adapter= review_adabter

        schedule_btn.setOnClickListener {
            var myintent = Intent(this, DoctorProfileSchedule::class.java)
            startActivity(myintent)
        }

        info_btn.setOnClickListener {
            var myintent = Intent(this, DoctorProfileInfo::class.java)
            startActivity(myintent)
        }

        review_btn.setOnClickListener {
            var myintent = Intent(this, DoctorProfileRiviews::class.java)
            startActivity(myintent)
        }

        home_btn.setOnClickListener {

            val myintent1 = Intent(this , Patient_Home_Screen::class.java)
            startActivity(myintent1)

        }

        setting_btn.setOnClickListener {

            val myintent2 = Intent(this , Patient_Setting_Screen::class.java)
            startActivity(myintent2)

        }
//
//        your_profile_btn.setOnClickListener {
//
//            val myintent3 = Intent(this , ::class.java)
//            startActivity(myintent3)
//
//        }
//
        calender_btn.setOnClickListener {

            val myintent4 = Intent(this ,Patient_Calender_Screen ::class.java)
            startActivity(myintent4)

        }

        chat_btn.setOnClickListener {

            val myintent5 = Intent(this ,ContactActivity ::class.java)
            startActivity(myintent5)

        }


    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_review_dialog, null)
        val reviewText = v.findViewById<EditText>(R.id.review_mess_d)
        val ratingValue = v.findViewById<RatingBar>(R.id.ratingBar3_review_d)
        val addReview = AlertDialog.Builder(this)
        addReview.setView(v)
        addReview.setPositiveButton("Ok"){
            dialog,_->
            val reviews = reviewText.text.toString()
            val rates = ratingValue.rating.toFloat()
            reviewList.add(Review(" $reviews" , rates))
            review_adabter.notifyDataSetChanged()
            Toast.makeText(this , " Adding Review Success " , Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addReview.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this , "Cancel" , Toast.LENGTH_SHORT).show()
        }
        addReview.create()
        addReview.show()
        
    }

    fun Iniliaztion()
    {
        schedule_btn = findViewById(R.id.schedule_btn1)
        info_btn = findViewById(R.id.info_btn1)
        review_btn = findViewById(R.id.review_btn1)
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
        adding_review = findViewById(R.id.adding_review_btn)
        review_recycle = findViewById(R.id.reviews_list)
        reviewList = ArrayList()
        review_adabter = ReviewAdapter(this,reviewList)
        mRef = FirebaseDatabase.getInstance().getReference()
    }
}