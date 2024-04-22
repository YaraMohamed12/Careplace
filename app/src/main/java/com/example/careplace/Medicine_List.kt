package com.example.careplace

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
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



class Medicine_List : AppCompatActivity() {
    private lateinit var mRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var medicineList: ArrayList<MedicineData>
    private lateinit var listViewMedicine: ListView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_list)
        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Medicine")
        listViewMedicine = findViewById(R.id.medcinelistview)
        medicineList = ArrayList()
        floatBtnDialog()
        val medicineAdapter = MedicineAdpater(this@Medicine_List, medicineList)
        listViewMedicine.adapter = medicineAdapter



    }

    private fun floatBtnDialog() {
        val myFlotbtn = findViewById<FloatingActionButton>(R.id.myFloatButton)
        myFlotbtn.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.medicine_dialog, null)
            val myDialogBuilder = AlertDialog.Builder(this)
            myDialogBuilder.setView(view)
            val alertDialog = myDialogBuilder.create()
            alertDialog.show()

            val medicineName = view.findViewById<EditText>(R.id.doz_name_dialog2)
            val medicineDate = view.findViewById<EditText>(R.id.doz_time_dialog2)
            val medicineNo = view.findViewById<EditText>(R.id.doz_no_dialog2)
            val medicineBtn = view.findViewById<Button>(R.id.btnadd)

            medicineBtn.setOnClickListener {
                val name = medicineName.text.toString()
                val time = medicineDate.text.toString()
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
}
