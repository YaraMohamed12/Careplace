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

class Booking_Patient_Adapter(context: Context, private val PateintList: List<Appointment>) :
    ArrayAdapter<Appointment>(context, 0, PateintList) {
    private val Newref = FirebaseDatabase.getInstance().getReference("user")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val myAppointment = PateintList[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = convertView ?: inflater.inflate(R.layout.patient_card_for_doc, parent, false)

        // Set the appointment date
        itemView.findViewById<TextView>(R.id.last_appointment_patient).text = myAppointment.schedule.date
        itemView.findViewById<TextView>(R.id.time_patient_card).text = myAppointment.schedule.time

        // Fetch doctor information from Firebase using the doctor's ID
        val Patientid = myAppointment.userid
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
