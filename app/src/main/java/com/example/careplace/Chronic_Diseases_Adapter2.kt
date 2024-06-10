package com.example.careplace


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Chronic_Diseases_Adapter2 (context : Context, chronicDiseasesFormList: ArrayList <Chronic_Diseases_Data>)
    : ArrayAdapter<Chronic_Diseases_Data>(context ,0 ,chronicDiseasesFormList ) {

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.simple_card, parent,false)
        val data: Chronic_Diseases_Data? = getItem(position)
        val disease_name : TextView = view.findViewById(R.id.operation_name_text)
        disease_name.text = data?.illness_name.toString()



        return view
    }



}