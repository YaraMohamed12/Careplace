package com.example.careplace

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.search.SearchBar


class Doctors_List : AppCompatActivity() {

    private lateinit var DoctorRecyclerView: RecyclerView
    private lateinit var Doctorlist: ArrayList<DUsers>
    private lateinit var adapter: LIstDoctorAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference
    private lateinit var searchbar : SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctors_list)
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




