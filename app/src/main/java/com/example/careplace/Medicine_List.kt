package com.example.careplace

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class Medicine_List : AppCompatActivity() {
    private lateinit var mRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var medicineList: ArrayList<MedicineData>
    private lateinit var listViewMedicine: ListView
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_list)
        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Medicine")
        listViewMedicine = findViewById(R.id.medcinelistview)

        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)

        medicineList = ArrayList()
        floatBtnDialog()
        val medicineAdapter = MedicineAdpater(this@Medicine_List, medicineList)
        listViewMedicine.adapter = medicineAdapter
        createNotificationChannel()



        home_btn.setOnClickListener {

            val myintent1 = Intent(this , Patient_Home_Screen::class.java)
            startActivity(myintent1)

        }

        setting_btn.setOnClickListener {

            val myintent2 = Intent(this ,Patient_Setting_Screen ::class.java)
            startActivity(myintent2)

        }
//
//        your_profile_btn.setOnClickListener {
//
//            val myintent3 = Intent(this , ::class.java)
//            startActivity(myintent3)
//
//        }
//
        calender_btn.setOnClickListener {

            val myintent4 = Intent(this ,Patient_Calender_Screen ::class.java)
            startActivity(myintent4)

        }

        chat_btn.setOnClickListener {

            val myintent5 = Intent(this ,ContactActivity ::class.java)
            startActivity(myintent5)

        }

    }



    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    private fun floatBtnDialog() {
        val myFlotbtn = findViewById<FloatingActionButton>(R.id.myFloatButton)
        myFlotbtn.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.medicine_dialog, null)
            val myDialogBuilder = AlertDialog.Builder(this)
            myDialogBuilder.setView(view)
            val alertDialog = myDialogBuilder.create()
            alertDialog.show()
            val medicinepicker = view.findViewById<TimePicker>(R.id.timePicker)
            val medicineName = view.findViewById<EditText>(R.id.doz_name_dialog1)
            val medicineNo = view.findViewById<EditText>(R.id.doz_no_dialog1)
            val medicineBtn = view.findViewById<Button>(R.id.btnadd)

              calculateTimeDifferenceInSeconds(medicinepicker)
            val intent = Intent(this, MyBroadcastReciver::class.java)
            pendingIntent = PendingIntent.getBroadcast(this, 224, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            medicineBtn.setOnClickListener {
                val name = medicineName.text.toString()
                val time = formatTimeToString(medicinepicker)
                val doz = medicineNo.text.toString()

                if (time.isNotEmpty() && name.isNotEmpty() && doz.isNotEmpty()) {
                    val id = mRef.push().key ?: ""
                    val myMedicine = MedicineData(name, "Number Doz : ${doz}", time,id)
                    mRef.child(id).setValue(myMedicine)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Medicine added successfully", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to add medicine: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Enter Name, Time, and Dose of Medicine", Toast.LENGTH_SHORT).show()
                }
                alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + calculateTimeDifferenceInSeconds(medicinepicker) * 1000,
                    pendingIntent)
                Toast.makeText(this, "Alarm is Activated", Toast.LENGTH_SHORT).show()

            }



        }
    }

    override fun onStart() {
        super.onStart()
        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                medicineList.clear()
                for (n in snapshot.children) {
                    val medicine = n.getValue(MedicineData::class.java)
                    medicine?.let { medicineList.add(0, it) }
                }
                val medicineAdapter = MedicineAdpater(this@Medicine_List, medicineList)
                listViewMedicine.adapter = medicineAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }
    fun formatTimeToString(timePicker: TimePicker): String {
        val hour = timePicker.hour
        val minute = timePicker.minute

        // Create a Calendar instance to hold the selected time
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        // Create a date format (e.g., "hh:mm a" for 12-hour format with AM/PM)
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        // Format the time using the SimpleDateFormat
        return dateFormat.format(calendar.time)
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "your_channel_id"
            val channelName = "Your Channel Name"
            val channelDescription = "Your Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun calculateTimeDifferenceInSeconds(timePicker: TimePicker) : Long {
        // Get the selected hour and minute from the TimePicker
        val hour = timePicker.hour
        val minute = timePicker.minute

        // Create a Calendar instance and set it to the selected time
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.set(Calendar.HOUR_OF_DAY, hour)
        selectedCalendar.set(Calendar.MINUTE, minute)
        selectedCalendar.set(Calendar.SECOND, 0) // Set seconds to 0 for precision

        // Get current time
        val currentCalendar = Calendar.getInstance()

        // Calculate the difference in milliseconds between selected time and current time
        val differenceInMillis = selectedCalendar.timeInMillis - currentCalendar.timeInMillis

        // Update the TextView with the calculated time difference in milliseconds

        return differenceInMillis / 1000
    }



}




