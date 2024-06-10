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

class Allergy_Adapter2 (context : Context, allergyFormList: ArrayList <Allergy_Data>)
    : ArrayAdapter<Allergy_Data>(context ,0 ,allergyFormList ) {

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.simple_card, parent,false)
        val data: Allergy_Data? = getItem(position)
        val allergy_name : TextView = view.findViewById(R.id.operation_name_text)
        allergy_name.text = data?.allergy_name.toString()



        return view
    }


}