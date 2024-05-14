package com.example.careplace

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class Patient_medicate_Adapter (context : Context, MedicationFormList : ArrayList <Medicatin_Form_Data>)
    : ArrayAdapter<Medicatin_Form_Data>(context ,0 ,MedicationFormList ) {
    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.medcition_only_card2, parent, false)
        val data: Medicatin_Form_Data? = getItem(position)
        val surgery_name: TextView = view.findViewById(R.id.name2_media2)
        val surgery_details: TextView = view.findViewById(R.id.date2_media2)


        surgery_name.text = data?.medicin_name.toString()
        surgery_details.text = data?.medicin_date.toString()

        return view
    }
}