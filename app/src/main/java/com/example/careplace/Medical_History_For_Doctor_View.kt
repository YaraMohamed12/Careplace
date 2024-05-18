package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Medical_History_For_Doctor_View : AppCompatActivity() {
    lateinit var  Pateintid : kotlin.String
    lateinit var pateint_surgery_list : ListView
    lateinit var pateint_Medication_list : ListView
    lateinit var pateint_Document_list : ListView
    private lateinit var listViewAllergies : ListView
    private lateinit var listViewChronicDisease: ListView
    lateinit var Surgerieslist : ArrayList<Surgeries_Form_Data>
    lateinit var Medictionlist : ArrayList<Medicatin_Form_Data>
    lateinit var Documnetlist : ArrayList<Doc_class_Data>
    lateinit var pateint_AllerigesList : ArrayList<Allergy_Data>
    lateinit var chorinc_dieaseList: ArrayList<Chronic_Diseases_Data>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_history_for_doctor_view)
        Pateintid = intent.getStringExtra("PateintId")!!
        Surgerieslist = ArrayList()
        Medictionlist = ArrayList()
        Documnetlist = ArrayList()
        chorinc_dieaseList = ArrayList()
        pateint_AllerigesList = ArrayList()
        retrivePatientSurgeries()
        retrivePatientMedication()
        retrivePatientDocumnet()
        retrivePatientAllergies()
        retrivePatientChronicDiseases()
        inisialization1()
    }


    private fun inisialization1() {
        pateint_Document_list = findViewById(R.id.Docment_listView2)
        pateint_surgery_list = findViewById(R.id.surgeries_listView2)
        pateint_Medication_list = findViewById(R.id.medication_listView2)
        listViewAllergies = findViewById(R.id.allergy_card)
        listViewChronicDisease = findViewById(R.id.illness_card)
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

    private fun retrivePatientChronicDiseases() {
        val mref = FirebaseDatabase.getInstance().getReference("user").child(Pateintid).child("Chronic Diseases")
        mref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                chorinc_dieaseList.clear()

                for (DataSnapshot in dataSnapshot.children) {
                    val Name = DataSnapshot.child("illness_name").getValue(String::class.java)
                    val disease_id = DataSnapshot.child("illness_id").getValue(String::class.java)


                    val Chronic_disease_data = Chronic_Diseases_Data(Name,disease_id)
                    chorinc_dieaseList.add(Chronic_disease_data)

                }
                val adapter = Chronic_Diseases_Adapter(this@Medical_History_For_Doctor_View, chorinc_dieaseList)
                listViewChronicDisease.adapter = adapter

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Medical_History_For_Doctor_View, "Failed to add Chronic disease", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun retrivePatientAllergies() {
        val mref = FirebaseDatabase.getInstance().getReference("user").child(Pateintid).child("allergies")
        mref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                pateint_AllerigesList.clear()

                for (DataSnapshot in dataSnapshot.children) {
                    val Name = DataSnapshot.child("allergy_name").getValue(String::class.java)
                    val allergies_id = DataSnapshot.child("allergy_id").getValue(String::class.java)


                    val Allergy_data = Allergy_Data(Name,allergies_id)
                    pateint_AllerigesList.add(Allergy_data)

                }
                val adapter = Allergy_Adapter(this@Medical_History_For_Doctor_View, pateint_AllerigesList)
                listViewAllergies.adapter = adapter

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Medical_History_For_Doctor_View, "Failed to add Allergy", Toast.LENGTH_SHORT).show()
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


