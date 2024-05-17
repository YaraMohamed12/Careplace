package com.example.careplace

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Booked_Doctor_Adapter(context: Context, private val DoctorList: List<Appointment>) :
    ArrayAdapter<Appointment>(context, 0, DoctorList) {

    private val Newref = FirebaseDatabase.getInstance().getReference("DUser")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val myAppointment = DoctorList[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = convertView ?: inflater.inflate(R.layout.doc_card_for_patient, parent, false)

        // Set the appointment date
        itemView.findViewById<TextView>(R.id.last_appointment_doc).text = myAppointment.schedule.date
        itemView.findViewById<TextView>(R.id.time_for_appoimnet).text = myAppointment.schedule.time

        // Fetch doctor information from Firebase using the doctor's ID
        val doctorId = myAppointment.userid
        Newref.child(doctorId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val doctorName = snapshot.child("dname").getValue(String::class.java)
                if (doctorName != null) {
                    itemView.findViewById<TextView>(R.id.Doctor_name_card).text = doctorName
                } else {
                    itemView.findViewById<TextView>(R.id.Doctor_name_card).text = "Unknown Doctor"
                    // Handle case where 'dname' is null or not found
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database read error
                Log.e("Booked_Doctor_Adapter", "Error fetching doctor information", error.toException())
                itemView.findViewById<TextView>(R.id.Doctor_name_card).text = "Doctor Data Unavailable"
            }
        })

        return itemView
    }
}
