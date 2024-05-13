package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
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

class Medical_History_For_Patient_View : AppCompatActivity() {
    private lateinit var mRef1: DatabaseReference
    private lateinit var mRef2: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var Medication_list: ArrayList<Medicatin_Form_Data>
    private lateinit var Surgery_list: ArrayList<Surgeries_Form_Data>
    private lateinit var listViewMedication: ListView
    private lateinit var listViewSurgeries: ListView
    lateinit var home_btn: ImageView
    lateinit var setting_btn: ImageView
    lateinit var your_profile_btn: ImageView
    lateinit var calender_btn: ImageView
    lateinit var chat_btn: ImageView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_history_patient_view)
        mAuth = FirebaseAuth.getInstance()
        mRef1 = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}")
        mRef2 = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}")
        listViewMedication = findViewById(R.id.medication_form_listView)
        listViewSurgeries = findViewById(R.id.surgeries_listView)
        Medication_list = ArrayList()
        Surgery_list = ArrayList()
        floatBtnDialog()
        iniliaztlisinter()
        cliciking()
        retrieveSurgeries()
        retrieveMediction()

    }



    private fun iniliaztlisinter() {
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn = findViewById(R.id.goto_chat)

    }

    private fun cliciking() {
        home_btn.setOnClickListener {
            val myintent1 = Intent(this, Patient_Home_Screen::class.java)
            startActivity(myintent1)
        }
        setting_btn.setOnClickListener {
            val myintent2 = Intent(this, Patient_Setting_Screen::class.java)
            startActivity(myintent2)
        }
        your_profile_btn.setOnClickListener {
            val myintent3 = Intent(this, My_Profile_Details_for_patient::class.java)
            startActivity(myintent3)
        }
        calender_btn.setOnClickListener {
            val myintent4 = Intent(this, Patient_Calender_Screen::class.java)
            startActivity(myintent4)
        }
        chat_btn.setOnClickListener {
            val myintent5 = Intent(this, ContactActivity::class.java)
            startActivity(myintent5)
        }
    }


    @SuppressLint("MissingInflatedId", "CutPasteId")
    private fun floatBtnDialog() {
        val myFlotbtn1 = findViewById<FloatingActionButton>(R.id.add_medicine)
        val myFlotbtn2 = findViewById<FloatingActionButton>(R.id.add_surgery)
        val myFlotbtn3 = findViewById<FloatingActionButton>(R.id.add_pic)
        myFlotbtn1.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.medication_and_surgeries_dialog, null)
            val myDialogBuilder = AlertDialog.Builder(this)
            myDialogBuilder.setView(view)
            val alertDialog = myDialogBuilder.create()
            alertDialog.show()
            val medicineName = view.findViewById<EditText>(R.id.name)
            val medicine_date = view.findViewById<EditText>(R.id.date)
            val add_medicine_Btn = view.findViewById<Button>(R.id.add_btn)

            add_medicine_Btn.setOnClickListener {
                val name = medicineName.text.toString()
                val date = medicine_date.text.toString()

                if (date.isNotEmpty() && name.isNotEmpty()) {
                    val id = mRef1.push().key ?: ""
                    val myMedicine = Medicatin_Form_Data(name, date, id)
                    mRef1.child("Medication").child(id).setValue(myMedicine)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Medicine added successfully", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this, "Failed to add medicine: ${e.message}", Toast.LENGTH_SHORT).show() }
                } else {
                    Toast.makeText(this, "Enter Name and Date", Toast.LENGTH_SHORT).show()
                }


            }
        }
        myFlotbtn2.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.medication_and_surgeries_dialog, null)
            val myDialogBuilder = AlertDialog.Builder(this)
            myDialogBuilder.setView(view)
            val alertDialog = myDialogBuilder.create()
            alertDialog.show()
            val surgery_Name = view.findViewById<EditText>(R.id.name)
            val surgery_date = view.findViewById<EditText>(R.id.date)
            val add_medicine_Btn = view.findViewById<Button>(R.id.add_btn)

            add_medicine_Btn.setOnClickListener {
                val name = surgery_Name.text.toString()
                val date = surgery_date.text.toString()

                if (date.isNotEmpty() && name.isNotEmpty()) {
                    val id = mRef2.push().key ?: ""
                    val mySurgery = Surgeries_Form_Data(name, date, id)
                    mRef2.child("Operation").child(id).setValue(mySurgery)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Surgery added successfully", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to add Surgery: ${e.message}", Toast.LENGTH_SHORT).show() }
                } else {
                    Toast.makeText(this, "Enter Name and Date", Toast.LENGTH_SHORT).show()
                }


            }
        }
        myFlotbtn3.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.medication_and_surgeries_dialog, null)
            val myDialogBuilder = AlertDialog.Builder(this)
            myDialogBuilder.setView(view)
            val alertDialog = myDialogBuilder.create()
            alertDialog.show()
            val surgery_Name = view.findViewById<EditText>(R.id.name)
            val surgery_date = view.findViewById<EditText>(R.id.date)
            val add_medicine_Btn = view.findViewById<Button>(R.id.add_btn)

            add_medicine_Btn.setOnClickListener {
                val name = surgery_Name.text.toString()
                val date = surgery_date.text.toString()

                if (date.isNotEmpty() && name.isNotEmpty()) {
                    val id = mRef2.push().key ?: ""
                    val mySurgery = Surgeries_Form_Data(name, date, id)
                    mRef2.child("Operation").child(id).setValue(mySurgery)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Surgery added successfully", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to add Surgery: ${e.message}", Toast.LENGTH_SHORT).show() }
                } else {
                    Toast.makeText(this, "Enter Name and Date", Toast.LENGTH_SHORT).show()
                }


            }
        }



    }
    private fun retrieveSurgeries() {
        val mAuth = FirebaseAuth.getInstance()
        val currentuserid = mAuth.currentUser?.uid
        val mref = FirebaseDatabase.getInstance().getReference("user").child(currentuserid!!).child("Operation")

        mref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Surgery_list.clear()

                for (DataSnapshot in dataSnapshot.children) {
                    val date = DataSnapshot.child("surgery_date").getValue(String::class.java)
                    val Name = DataSnapshot.child("surgery_name").getValue(String::class.java)
                    val Surgery_id = DataSnapshot.child("surgery_id").getValue(String::class.java)


                        val surgery_data = Surgeries_Form_Data(Name,date,Surgery_id)
                            Surgery_list.add(surgery_data)

                }


                val adapter = Surgeries_Form_Adapter(this@Medical_History_For_Patient_View, Surgery_list)
               listViewSurgeries.adapter = adapter

            }

            override fun onCancelled(databaseError: DatabaseError) {


                Toast.makeText(this@Medical_History_For_Patient_View, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun retrieveMediction() {
        val mAuth = FirebaseAuth.getInstance()
        val currentuserid = mAuth.currentUser?.uid
        val mref = FirebaseDatabase.getInstance().getReference("user").child(currentuserid!!).child("Medication")

        mref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
               Medication_list.clear()

                for (DataSnapshot in dataSnapshot.children) {
                    val date = DataSnapshot.child("medicate_date").getValue(String::class.java)
                    val Name = DataSnapshot.child("medicate_name").getValue(String::class.java)
                    val Surgery_id = DataSnapshot.child("medicate_id").getValue(String::class.java)


                    val Medication_data = Medicatin_Form_Data(Name,date,Surgery_id)
                    Medication_list.add(Medication_data)

                }


                val adapter = Medication_Form_Adapter(this@Medical_History_For_Patient_View, Medication_list)
                listViewMedication.adapter = adapter

            }

            override fun onCancelled(databaseError: DatabaseError) {


                Toast.makeText(this@Medical_History_For_Patient_View, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })
    }
    }


