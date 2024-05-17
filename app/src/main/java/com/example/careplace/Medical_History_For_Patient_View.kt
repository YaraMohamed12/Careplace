package com.example.careplace

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class Medical_History_For_Patient_View : AppCompatActivity() {
    private lateinit var mRef1: DatabaseReference
    private lateinit var mRef2: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var Medication_list: ArrayList<Medicatin_Form_Data>
    private lateinit var Allegies_list: ArrayList<Allergy_Data>
    private lateinit var Chronic_Diseases_list: ArrayList<Chronic_Diseases_Data>
    private lateinit var Surgery_list: ArrayList<Surgeries_Form_Data>
    private lateinit var Docment_list : ArrayList<Doc_class_Data>
    private lateinit var listViewMedication: ListView
    private lateinit var listViewSurgeries: ListView
    private lateinit var listViewDocment : ListView
    private lateinit var listViewAllergies : ListView
    private lateinit var listViewChronicDisease: ListView
    lateinit var home_btn: ImageView
    private val GALLERY_REQUEST_CODE = 100
    lateinit var setting_btn: ImageView
    lateinit var your_profile_btn: ImageView
    lateinit var calender_btn: ImageView
    lateinit var chat_btn: ImageView
    lateinit var doc_txt : EditText
    lateinit var add_img : ImageView
    lateinit var add_btn : Button
    private lateinit var mRef: DatabaseReference



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_history_patient_view)
        mAuth = FirebaseAuth.getInstance()
        mRef1 = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}")
        mRef2 = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}")
        listViewMedication = findViewById(R.id.medication_form_listView)
        listViewSurgeries = findViewById(R.id.surgeries_listView)
        listViewDocment =findViewById(R.id.Docment_listView)
        listViewAllergies = findViewById(R.id.Allerige_text)
        listViewChronicDisease = findViewById(R.id.Chroinc_disease)
        Medication_list = ArrayList()
        Surgery_list = ArrayList()
        Docment_list = ArrayList()
        Chronic_Diseases_list = ArrayList()
        Allegies_list = ArrayList()
        retrieveAllergies()
        retrieveChronicDiseases()
        floatBtnDialog()
        iniliaztlisinter()
        cliciking()
        retrieveSurgeries()
        retrieveMedication()
        retrieveDoc()
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
            val myintent5 = Intent(this, ContactActivity_For_Patient::class.java)
            startActivity(myintent5)
        }

    }


    @SuppressLint("MissingInflatedId", "CutPasteId")
    private fun floatBtnDialog() {
        val myFlotbtn1 = findViewById<FloatingActionButton>(R.id.add_medicine)
        val myFlotbtn2 = findViewById<FloatingActionButton>(R.id.add_surgery)
        val myFlotbtn3 = findViewById<FloatingActionButton>(R.id.add_pic)
        val myFloayingbtn4 = findViewById<FloatingActionButton>(R.id.add_your_illness)
        val myFloayingbtn5 = findViewById<FloatingActionButton>(R.id.add_allergy)
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
            val view = layoutInflater.inflate(R.layout.form_imgae_dialog, null)
            val myDialogBuilder = AlertDialog.Builder(this)
            myDialogBuilder.setView(view)
            val alertDialog = myDialogBuilder.create()
            alertDialog.show()
             doc_txt = view.findViewById(R.id.doc_txt)
            add_btn = view.findViewById<Button>(R.id.upimagpt)
             add_img = view.findViewById(R.id.xrayimg)
            add_img.setOnClickListener {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
            }



        }

        myFloayingbtn4.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.simple_dialog, null)
            val myDialogBuilder = AlertDialog.Builder(this)
            myDialogBuilder.setView(view)
            val alertDialog = myDialogBuilder.create()
            alertDialog.show()
            val disease_name = view.findViewById<EditText>(R.id.operation_name_edit)
            val add_illness_Btn = view.findViewById<Button>(R.id.adding)

            add_illness_Btn.setOnClickListener {
                val name = disease_name.text.toString()

                if (name.isNotEmpty() && name.isNotEmpty()) {
                    val id = mRef1.push().key ?: ""
                    val myillness = Chronic_Diseases_Data(name, id)
                    mRef1.child("Chronic Diseases").child(id).setValue(myillness)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Chronic disease added successfully", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this, "Failed to add Chronic disease: ${e.message}", Toast.LENGTH_SHORT).show() }
                } else {
                    Toast.makeText(this, " Enter Name of Disease ", Toast.LENGTH_SHORT).show()
                }


            }
        }

        myFloayingbtn5.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.simple_dialog, null)
            val myDialogBuilder = AlertDialog.Builder(this)
            myDialogBuilder.setView(view)
            val alertDialog = myDialogBuilder.create()
            alertDialog.show()
            val allergy_name = view.findViewById<EditText>(R.id.operation_name_edit)
            val add_allergy_Btn = view.findViewById<Button>(R.id.adding)

            add_allergy_Btn.setOnClickListener {
                val name = allergy_name.text.toString()

                if (name.isNotEmpty() && name.isNotEmpty()) {
                    val id = mRef1.push().key ?: ""
                    val myallergy = Allergy_Data(name, id)
                    mRef1.child("allergies").child(id).setValue(myallergy)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Allergy  added successfully", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this, "Failed to add Allergy : ${e.message}", Toast.LENGTH_SHORT).show() }
                } else {
                    Toast.makeText(this, " Enter Your Allergy ", Toast.LENGTH_SHORT).show()
                }


            }
        }


    }
    private fun retrieveAllergies() {
        val mAuth = FirebaseAuth.getInstance()
        val currentuserid = mAuth.currentUser?.uid
        val mref = FirebaseDatabase.getInstance().getReference("user").child(currentuserid!!).child("allergies")
        mref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Allegies_list.clear()

                for (DataSnapshot in dataSnapshot.children) {
                    val Name = DataSnapshot.child("allergy_name").getValue(String::class.java)
                    val allergies_id = DataSnapshot.child("allergy_id").getValue(String::class.java)


                    val allergiess_data = Allergy_Data(Name,allergies_id)
                    Allegies_list.add(allergiess_data)

                }
                val adapter = Allergy_Adapter(this@Medical_History_For_Patient_View, Allegies_list)
                listViewAllergies.adapter = adapter

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Medical_History_For_Patient_View, "Failed to retrieve Allergy ", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun retrieveChronicDiseases() {
        val mAuth = FirebaseAuth.getInstance()
        val currentuserid = mAuth.currentUser?.uid
        val mref = FirebaseDatabase.getInstance().getReference("user").child(currentuserid!!).child("Chronic Diseases")
        mref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Chronic_Diseases_list.clear()

                for (DataSnapshot in dataSnapshot.children) {
                    val Name = DataSnapshot.child("illness_name").getValue(String::class.java)
                    val disease_id = DataSnapshot.child("illness_id").getValue(String::class.java)


                    val chronic_disease_data = Chronic_Diseases_Data(Name,disease_id)
                    Chronic_Diseases_list.add(chronic_disease_data)

                }
                val adapter = Chronic_Diseases_Adapter(this@Medical_History_For_Patient_View, Chronic_Diseases_list)
                listViewChronicDisease.adapter = adapter

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Medical_History_For_Patient_View, "Failed to retrieve Chronic Diseases ", Toast.LENGTH_SHORT).show()
            }
        })
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
    private fun retrieveMedication() {
        val mAuth = FirebaseAuth.getInstance()
        val currentuserid = mAuth.currentUser?.uid
        val mref = FirebaseDatabase.getInstance().getReference("user").child(currentuserid!!).child("Medication")
        mref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Medication_list.clear()

                for (DataSnapshot in dataSnapshot.children) {
                    val date = DataSnapshot.child("medicin_date").getValue(String::class.java)
                    val Name = DataSnapshot.child("medicin_name").getValue(String::class.java)
                    val Surgery_id = DataSnapshot.child("medicin_id").getValue(String::class.java)


                    val Medicate_data = Medicatin_Form_Data(Name,date,Surgery_id)
                    Medication_list.add(Medicate_data)

                }
                val adapter = Medication_Form_Adapter(this@Medical_History_For_Patient_View, Medication_list)
                listViewMedication.adapter = adapter

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Medical_History_For_Patient_View, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            val storageRef = FirebaseStorage.getInstance().reference
            val currentUser = FirebaseAuth.getInstance().currentUser
            val userRef = FirebaseDatabase.getInstance().getReference("user")
            val imgid = userRef.push().key ?: ""

            if (selectedImageUri != null) {
                val photoRef: StorageReference = storageRef.child("users_rays").child(imgid)
                Glide.with(this@Medical_History_For_Patient_View)
                    .load(selectedImageUri)
                    .into(add_img)
                add_btn.setOnClickListener {
                photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener { uploadTask ->
                        photoRef.downloadUrl.addOnSuccessListener { downloadUri ->
                            val docName = doc_txt.text.toString()
                            val docData = Doc_class_Data(docName, downloadUri.toString(), imgid)

                            currentUser?.uid?.let { uid ->
                                userRef.child(uid).child("user_rays").child(imgid).setValue(docData)
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(this, "upload succesfully", Toast.LENGTH_SHORT).show()
            }
            }
        }
    }

    private fun retrieveDoc() {
        val mAuth = FirebaseAuth.getInstance()
        val currentuserid = mAuth.currentUser?.uid
        val mref = FirebaseDatabase.getInstance().getReference("user").child(currentuserid!!).child("user_rays")
        mref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Docment_list.clear()

                for (DataSnapshot in dataSnapshot.children) {
                    val DocUri = DataSnapshot.child("doc_uri").getValue(String::class.java)
                    val Name = DataSnapshot.child("doc_name").getValue(String::class.java)
                    val Doc_id = DataSnapshot.child("doc_id").getValue(String::class.java)


                    val Doc_data = Doc_class_Data(Name,DocUri,Doc_id)
                    Docment_list.add(Doc_data)

                }
                val adapter = Doc_Adapter(this@Medical_History_For_Patient_View, Docment_list)
                listViewDocment.adapter = adapter

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Medical_History_For_Patient_View, "Failed to retrieve schedules", Toast.LENGTH_SHORT).show()
            }
        })
    }








}


