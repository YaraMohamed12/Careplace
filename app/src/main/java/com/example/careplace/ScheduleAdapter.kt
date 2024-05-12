package com.example.careplace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ScheduleAdapter(context: Context, private val scheduleList: List<Schedule>) :
    ArrayAdapter<Schedule>(context, 0, scheduleList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val schedule = scheduleList[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val currentuser = FirebaseAuth.getInstance().currentUser
        val mRef = FirebaseDatabase.getInstance().getReference("/Doctors_schedule/${currentuser?.uid}/schedules")
        val itemView = convertView ?: inflater.inflate(R.layout.appointment_card, parent, false)
        itemView.findViewById<TextView>(R.id.date_of_appointment).text = schedule.date
        itemView.findViewById<TextView>(R.id.time_of_appointment).text = schedule.time
        val del_btn = itemView.findViewById<Button>(R.id.remove_book_btn)
        del_btn.setOnClickListener {
            val scheduleId = schedule.sui
            val scheduleRef = mRef.child(scheduleId)

            scheduleRef.removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Schedule deleted", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to delete schedule: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        return itemView
    }
}

