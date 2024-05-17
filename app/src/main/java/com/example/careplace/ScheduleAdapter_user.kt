package com.example.careplace

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import java.util.UUID

class ScheduleAdapter_user(context: Context, private val scheduleList: List<Schedule>) :
    ArrayAdapter<Schedule>(context, 0, scheduleList) {
    private val sharedPreferences = context.getSharedPreferences("share_data", Context.MODE_PRIVATE)
    private val Doctorid = sharedPreferences.getString("Doctorid", "")
    @SuppressLint("SuspiciousIndentation")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // firebase inilaztion
        val mRef = FirebaseDatabase.getInstance().getReference("user")
        val Bref = FirebaseDatabase.getInstance().getReference("/Doctors_schedule/$Doctorid/schedules")
        val Aref = FirebaseDatabase.getInstance().getReference("Doctor_Booked")
        val ChatRef = FirebaseDatabase.getInstance().getReference("Chatid")
        val mAuth = FirebaseAuth.getInstance()
        val currentuserid = mAuth.currentUser?.uid //patient , doctor id , schduel class data



        // retrive data to list item view
        val schedule = scheduleList[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = convertView ?: inflater.inflate(R.layout.user_appointment_card, parent, false)
        itemView.findViewById<TextView>(R.id.date_of_appointment2).text = schedule.date // date from schedule class data
        itemView.findViewById<TextView>(R.id.time_of_appointment2).text = schedule.time
        val schudelid = schedule.sui
        val book_btn = itemView.findViewById<Button>(R.id.book_btn)
        val Patientappointment = Appointment(schedule, Doctorid ?: "")
        val Doctorappointment = Appointment(schedule, currentuserid!!)
        book_btn.setOnClickListener {

            mRef.child(currentuserid).child("userbook").child(schudelid).setValue(Patientappointment).addOnSuccessListener {
                Toast.makeText(context, "Booking Appoimntment is Succesful", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Booking Appomintment failed", Toast.LENGTH_SHORT).show()

            }
         Aref.child(Doctorid!!).child("userbook").child(schudelid).setValue(Doctorappointment).addOnSuccessListener {
             Toast.makeText(context, "Adding the pateint to doctor", Toast.LENGTH_SHORT).show()
         }.addOnFailureListener {
             Toast.makeText(context, "Booking Appomintment failed", Toast.LENGTH_SHORT).show()
         }
            val scheduleRef = Bref.child(schudelid)
            scheduleRef.removeValue()
          ChatRef.child(currentuserid).child(Doctorid).setValue(Doctorid)
            ChatRef.child(Doctorid).child(currentuserid).setValue(currentuserid)



            val tokenRef = FirebaseDatabase.getInstance().getReference("DUser").child(Doctorid).child("fcmToken")
                tokenRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val doctorToken = dataSnapshot.getValue(String::class.java)
                        if (doctorToken != null) {
                            FirebaseMessaging.getInstance().send(
                                RemoteMessage.Builder(doctorToken)
                                    .setMessageId(UUID.randomUUID().toString())
                                    .addData("title", "Your Appoiment is book scuessfully")
                                    .addData("body", "there is patient book your appoiment")
                                    .build()
                            )



                        } else {

                            Toast.makeText(context, "Doctor's token not found", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                        Toast.makeText(context, "Failed to retrieve doctor's token", Toast.LENGTH_SHORT).show()
                    }
                })


        }

        return itemView
    }


}