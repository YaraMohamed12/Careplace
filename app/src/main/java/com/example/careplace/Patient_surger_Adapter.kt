package com.example.careplace

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class Patient_surger_Adapter (context : Context, surgeryFormList : ArrayList <Surgeries_Form_Data>)
    : ArrayAdapter<Surgeries_Form_Data>(context ,0 ,surgeryFormList )  {
    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.surgeries_card2, parent, false)
        val data: Surgeries_Form_Data? = getItem(position)
        val surgery_name: TextView = view.findViewById(R.id.name_surgery)
        val surgery_details: TextView = view.findViewById(R.id.date_surgery)
        surgery_name.text = data?.surgery_name.toString()
        surgery_details.text = data?.surgery_date.toString()
        return view
    }
}