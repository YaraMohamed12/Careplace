package com.example.careplace

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.util.UUID

class ScheduleAdapter_user(context: Context, private val scheduleList: List<Schedule>) :
    ArrayAdapter<Schedule>(context, 0, scheduleList) {
    private val sharedPreferences = context.getSharedPreferences("share_data", Context.MODE_PRIVATE)
    private val Doctorid = sharedPreferences.getString("Doctorid", "")
    @SuppressLint("SuspiciousIndentation")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // firebase inilaztion
        val mRef = FirebaseDatabase.getInstance().getReference("user")
        val mRef2 = FirebaseDatabase.getInstance().getReference("DUser")
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


            var patient_name : String = ""
            var Doctor_name : String = ""
            var Doctor_img : String = ""
            var patient_img : String = ""
            mRef.child(currentuserid).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    patient_name = snapshot.child("name").getValue(String::class.java)!!
                    patient_img = snapshot.child("profileImageUrl").getValue(String::class.java) ?:" "
                    val patient_info = Contact_info_Data(currentuserid,patient_name,patient_img)
                    ChatRef.child(Doctorid).child(currentuserid).setValue(patient_info)
                }override fun onCancelled(error: DatabaseError) {} })


            mRef2.child(Doctorid).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Doctor_name = snapshot.child("dname").getValue(String::class.java)!!
                    Doctor_img = snapshot.child("profileImageUrl").getValue(String::class.java) ?: ""
                    val doctor_info = Contact_info_Data(Doctorid,Doctor_name,Doctor_img)
                    ChatRef.child(currentuserid).child(Doctorid).setValue(doctor_info)
                }
                override fun onCancelled(error: DatabaseError) {} })


//            val doctorTokenRef = FirebaseDatabase.getInstance()
//                .getReference("DUser").child(Doctorid).child("fcmToken")
//            doctorTokenRef.get().addOnSuccessListener { dataSnapshot ->
//                val doctorToken = dataSnapshot.getValue(String::class.java)
//                if (doctorToken != null) {
//                    sendNotification(context, doctorToken, "Booked Your Appointment", "A patient has booked an appointment with you.")
//
//                } else {
//                    Toast.makeText(context, "Doctor token not found", Toast.LENGTH_SHORT).show()
//                }
//            }.addOnFailureListener { e ->
//                Toast.makeText(context, "Failed to retrieve doctor token", Toast.LENGTH_SHORT).show()
//            }
        }

        return itemView
    }


//    fun sendNotification(context: Context, token: String, title: String, message: String) {
//        val requestQueue = Volley.newRequestQueue(context)
//        val url = "https://fcm.googleapis.com/fcm/send"
//
//        val jsonObject = JSONObject()
//        val notification = JSONObject()
//
//        notification.put("title", title)
//        notification.put("body", message)
//        jsonObject.put("to", token)
//        jsonObject.put("notification", notification)
//
//        val request = object : JsonObjectRequest(Request.Method.POST, url, jsonObject,
//            Response.Listener { response ->
//                Toast.makeText(context, "Notification sent", Toast.LENGTH_SHORT).show()
//            },
//            Response.ErrorListener { error ->
//                Toast.makeText(context, "Failed to send notification", Toast.LENGTH_SHORT).show()
//                error.printStackTrace()
//            }) {
//            override fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Content-Type"] = "application/json"
//                headers["Authorization"] = "key=AAAA0Pk4dWQ:APA91bFqeFVP26kpOQaC40RC_fj0spbkt63WrTdxOI-dTxdK0OB4vFDjSWvReQP3arDv8MXC8ds8qNoP2ICFMrc27LR81uTNpPCUpQjhUcp9cgyR0Gg508Om96bkZa2JgPHaCNXztdhR"
//                return headers
//            }
//        }
//
//        requestQueue.add(request)
//    }









}