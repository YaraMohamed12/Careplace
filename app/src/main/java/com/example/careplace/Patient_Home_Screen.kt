package com.example.careplace

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class Patient_Home_Screen : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var user_image: ImageView
    private lateinit var patname: TextView
    private lateinit var database: FirebaseDatabase
    private lateinit var mref: DatabaseReference
    private lateinit var myathun: FirebaseAuth
    private var Appuser: FirebaseUser? = null
    lateinit var Goto_chat : ImageView
    lateinit var select_doc : FrameLayout
    lateinit var select_Mdeicine : FrameLayout
    lateinit var testbtn : ImageView
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_home_screen)
        ImportUserData()
        user_image = findViewById(R.id.select_image)
        patname = findViewById(R.id.textView13)
        Goto_chat = findViewById(R.id.goto_chat)
        select_doc = findViewById(R.id.choose_doctor)
        select_Mdeicine = findViewById(R.id.choose_medicine)
        toolbar = findViewById(R.id.mytoolbar)
        toolbar.inflateMenu(R.menu.main_menu)
        testbtn = findViewById(R.id.profile_icon_bar)

        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
//        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)

        menuInflate()
        val myreadata = ReadData()
        mref.addValueEventListener(myreadata)
        Goto_chat.setOnClickListener {
            val intent = Intent(this,ContactActivity::class.java)
            startActivity(intent)
        }

        select_doc.setOnClickListener {
            val my_intent = Intent(this,Choose_your_doc::class.java)
            startActivity(my_intent)
        }
        select_Mdeicine.setOnClickListener {
            val my_intent = Intent(this,Medicine_List::class.java)
            startActivity(my_intent)
        }


        home_btn.setOnClickListener {

            val myintent1 = Intent(this , Patient_Home_Screen::class.java)
            startActivity(myintent1)

        }

        setting_btn.setOnClickListener {

            val myintent2 = Intent(this ,SettingScreen ::class.java)
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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val channelId = "my_channel_id"
            val channelName = "My Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "This is my notification channel"
            }

            // Register the channel with the system
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
        testbtn.setOnClickListener {
            val title = "Care place"
            val message = "time for take your medicine"
            showNotification(this, title, message)
        }

    }

    private fun menuInflate() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.Log_out) {
                FirebaseAuth.getInstance().signOut()
                val myIntent = Intent(this@Patient_Home_Screen, DocOrPat::class.java)
                startActivity(myIntent)
                true
            } else {
                false
            }
        }
    }

    private fun ImportUserData() {
        database = FirebaseDatabase.getInstance()
        myathun = FirebaseAuth.getInstance()
        Appuser = myathun.currentUser
        if (Appuser != null)  {
            mref = database.getReference("user/${myathun.currentUser!!.uid}/name")


        }
    }

    inner class ReadData : ValueEventListener {
        // export userdata from database
        override fun onDataChange(snapshot: DataSnapshot) {
            val username = snapshot.getValue(String::class.java)
            patname.text = username
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle onCancelled
        }
    }
    fun showNotification(context: Context, title: String, message: String) {
        val channelId = "my_channel_id"
        // Create a notification builder with custom sound
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        // Show the notification
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(/* notificationId */ 1, builder.build())
    }
}
