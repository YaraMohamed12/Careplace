package com.example.careplace

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Doctors_List : AppCompatActivity() {
    private lateinit var new_Recycle_view :RecyclerView
    private lateinit var doc_list : ArrayList<New_Cards>
    private lateinit var adapter1: ListAdabter
    lateinit var crd_img : Array<Int>
    lateinit var c_name : Array<String>
    lateinit var C_spec : Array<String>
    lateinit var view_btn : Button
    lateinit var rate : RatingBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctors_list)


        doc_list = ArrayList()
        adapter1 = ListAdabter(this, doc_list)
        new_Recycle_view  = findViewById(R.id.doc_list_recycle_view)
        new_Recycle_view .layoutManager = LinearLayoutManager(this)
        new_Recycle_view .adapter = adapter1

    }
}