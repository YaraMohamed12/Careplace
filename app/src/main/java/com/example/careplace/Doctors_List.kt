package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.String


class Doctors_List : AppCompatActivity() {
    private lateinit var DoctorRecyclerView: RecyclerView
    private lateinit var Doctorlist: ArrayList<DUsers>
    private lateinit var adapter: LIstDoctorAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference
    private lateinit var searchbar : SearchView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView
    lateinit var home_btn : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctors_list)
        inisialization()
        ClickAction()
        mAuth = FirebaseAuth.getInstance()
        val specf = intent.getStringExtra("Spec")
        mRef = FirebaseDatabase.getInstance().getReference("/${specf.toString()}/Duser")
        Doctorlist = ArrayList()
        adapter = LIstDoctorAdapter(Doctorlist)
        DoctorRecyclerView = findViewById(R.id.doc_list_recycle_view)
       DoctorRecyclerView.layoutManager = LinearLayoutManager(this)
        DoctorRecyclerView.adapter = adapter
        ListingDoctorFromFirebase()

        searchbar = findViewById(R.id.search_bar)
        searchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchbar.clearFocus()
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })


    }
    private fun inisialization() {

        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
    }
    private fun ClickAction() {
        home_btn.setOnClickListener {
            val myintent1 = Intent(this , Patient_Home_Screen::class.java)
            startActivity(myintent1)
        }
        setting_btn.setOnClickListener {
            val myintent2 = Intent(this , Patient_Setting_Screen::class.java)
            startActivity(myintent2)
        }
        your_profile_btn.setOnClickListener {
            val myintent3 = Intent(this , My_Profile_Details_for_patient::class.java)
            startActivity(myintent3)
        }
        calender_btn.setOnClickListener {
            val myintent4 = Intent(this ,Patient_Calender_Screen ::class.java)
            startActivity(myintent4)
        }
        chat_btn.setOnClickListener {
            val myintent5 = Intent(this ,ContactActivity_For_Patient ::class.java)
            startActivity(myintent5)
        }
    }


    private fun ListingDoctorFromFirebase() {
        mRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Doctorlist.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val currentUser = postSnapshot.getValue(DUsers::class.java)
                    currentUser?.let { Doctorlist.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Database error: ${error.message}")
            }
        })
    }


}




