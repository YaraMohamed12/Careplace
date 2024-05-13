package com.example.careplace

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Booking_Patient_Adapter(context: Context, private val PateintList: List<Appointment>) :
    ArrayAdapter<Appointment>(context, 0, PateintList) {
    private val Newref = FirebaseDatabase.getInstance().getReference("user")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val myAppointment = PateintList[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = convertView ?: inflater.inflate(R.layout.patient_card_for_doc, parent, false)
        val detail_btn = itemView.findViewById<TextView>(R.id.details_btn)
        val Patientid = myAppointment.userid
        detail_btn.setOnClickListener {
             val intent =  Intent(context , Patient_Detail::class.java)
            intent.putExtra("PateintId",Patientid)
            context.startActivity(intent)
        }

        // Set the appointment date
        itemView.findViewById<TextView>(R.id.last_appointment_patient).text = myAppointment.schedule.date
        itemView.findViewById<TextView>(R.id.time_patient_card).text = myAppointment.schedule.time

        // Fetch doctor information from Firebase using the doctor's ID

        Newref.child(Patientid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val doctorName = snapshot.child("name").getValue(String::class.java)
                if (doctorName != null) {
                    itemView.findViewById<TextView>(R.id.patient_name_card).text = doctorName
                } else {
                    itemView.findViewById<TextView>(R.id.patient_name_card).text = "Unknown Patient"
                    // Handle case where 'name' is null or not found
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database read error
                Log.e("Booked_Doctor_Adapter", "Error fetching doctor information", error.toException())
                itemView.findViewById<TextView>(R.id.patient_name_card).text = "Patient Data Unavailable"
            }
        })



        return itemView
    }
}
