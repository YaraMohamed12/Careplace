package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Medical_History_For_Doctor_View : AppCompatActivity() {
    lateinit var  Pateintid : String
    lateinit var pateint_surgery_list : ListView
    lateinit var pateint_Medication_list : ListView
    lateinit var pateint_Document_list : ListView
    lateinit var Surgerieslist : ArrayList<Surgeries_Form_Data>
    lateinit var Medictionlist : ArrayList<Medicatin_Form_Data>
    lateinit var Documnetlist : ArrayList<Doc_class_Data>
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_history_for_doctor_view)
        Pateintid = intent.getStringExtra("PateintId")!!
        pateint_Document_list = findViewById(R.id.Docment_listView2)
        pateint_surgery_list = findViewById(R.id.surgeries_listView2)
        pateint_Medication_list = findViewById(R.id.medication_listView2)
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)

        Surgerieslist = ArrayList()
        Medictionlist = ArrayList()
        Documnetlist = ArrayList()
        retrivePatientSurgeries()
        retrivePatientMedication()
        retrivePatientDocumnet()
        buttonlistener()
    }

    private fun buttonlistener() {
        home_btn.setOnClickListener {
            val myintent1 = Intent(this , Doctor_Home_Screen::class.java)
            startActivity(myintent1)
        }
        setting_btn.setOnClickListener {
            val myintent2 = Intent(this , Doctor_Setting_Screen::class.java)
            startActivity(myintent2)
        }
        your_profile_btn.setOnClickListener {
            val myintent3 = Intent(this , My_Profile_Details_for_doc::class.java)
            startActivity(myintent3)
        }
        calender_btn.setOnClickListener {
            val myintent4 = Intent(this , Doctor_Calender_Screen::class.java)
            startActivity(myintent4)
        }
        chat_btn.setOnClickListener {
            val myintent5 = Intent(this , ContactActivity_For_Doctor::class.java)
            startActivity(myintent5)
        }
    }

    private fun retrivePatientMedication() {
        val mref = FirebaseDatabase.getInstance().getReference("user").child(Pateintid).child("Medication")
        mref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Medictionlist.clear()

                for (DataSnapshot in dataSnapshot.children) {
                    val date = DataSnapshot.child("medicin_date").getValue(String::class.java)
                    val Name = DataSnapshot.child("medicin_name").getValue(String::class.java)
                    val Surgery_id = DataSnapshot.child("medicin_id").getValue(String::class.java)


                    val Medicate_data = Medicatin_Form_Data(Name,date,Surgery_id)
                    Medictionlist.add(Medicate_data)

                }
                val adapter = Patient_medicate_Adapter(this@Medical_History_For_Doctor_View, Medictionlist)
                pateint_Medication_list.adapter = adapter

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Medical_History_For_Doctor_View, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun retrivePatientDocumnet() {
        val mref = FirebaseDatabase.getInstance().getReference("user").child(Pateintid).child("user_rays")
        mref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Documnetlist.clear()

                for (DataSnapshot in dataSnapshot.children) {
                    val DocUri = DataSnapshot.child("doc_uri").getValue(String::class.java)
                    val Name = DataSnapshot.child("doc_name").getValue(String::class.java)
                    val Doc_id = DataSnapshot.child("doc_id").getValue(String::class.java)


                    val Doc_data = Doc_class_Data(Name,DocUri,Doc_id)
                    Documnetlist.add(Doc_data)

                }
                val adapter = Pateint_Document_Adapter(this@Medical_History_For_Doctor_View, Documnetlist)
                pateint_Document_list.adapter = adapter

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Medical_History_For_Doctor_View, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun retrivePatientSurgeries() {

        val mref = FirebaseDatabase.getInstance().getReference("user").child(Pateintid).child("Operation")
        mref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Surgerieslist.clear()

                for (DataSnapshot in dataSnapshot.children) {
                    val date = DataSnapshot.child("surgery_date").getValue(String::class.java)
                    val Name = DataSnapshot.child("surgery_name").getValue(String::class.java)
                    val Surgery_id = DataSnapshot.child("surgery_id").getValue(String::class.java)


                    val surgery_data = Surgeries_Form_Data(Name,date,Surgery_id)
                    Surgerieslist.add(surgery_data)

                }
                val adapter = Patient_surger_Adapter(this@Medical_History_For_Doctor_View, Surgerieslist)
                pateint_surgery_list.adapter = adapter

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Medical_History_For_Doctor_View, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })

    }
    }


