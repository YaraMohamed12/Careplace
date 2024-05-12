package com.example.careplace

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Surgeries_Form_Adapter (context : Context, surgeryFormList : ArrayList <Surgeries_Form_Data>)
    : ArrayAdapter<Surgeries_Form_Data>(context ,0 ,surgeryFormList ) {
        @SuppressLint("MissingInflatedId")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.medication_and_surgeries_card, parent,false)
        val data: Surgeries_Form_Data? = getItem(position)
        val surgery_name : TextView = view.findViewById(R.id.name2)
        val surgery_details : TextView = view.findViewById(R.id.date2)
        val Uptbtn : Button = view.findViewById(R.id.edit_btn)
//        val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
//        val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Surgery")
        surgery_name.text = data?.surgery_name.toString()
        surgery_details.text = data?.surgery_date.toString()

        Uptbtn.setOnClickListener {
            showUpdateDialog(data)
        }

        return view
    }

    @SuppressLint("MissingInflatedId")
    private fun showUpdateDialog(data: Surgeries_Form_Data?) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.medication_and_surgeries_dialog, null)
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setView(dialogView)
            val alertDialog = dialogBuilder.create()
            alertDialog.show()
            val name: EditText = dialogView.findViewById(R.id.name1)
            val details_op: EditText = dialogView.findViewById(R.id.date1)
            val updBtn: Button = dialogView.findViewById(R.id.upd_btn)
            val delBtn: Button = dialogView.findViewById(R.id.remove_btn)
            name.setText(data!!.surgery_name)
            details_op.setText(data.surgery_date)

            updBtn.setOnClickListener {
                val mAuth = FirebaseAuth.getInstance()
                val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Operations")

                // Ensure data is not null before attempting update
                data?.let {
                    val newName = name.text.toString().trim()
                    val new_detail = details_op.text.toString().trim()

                    // Update the medicine data with the new values
                    val updatedData = MedicineData( newName, new_detail,it.surgery_id)

                    // Perform the update in the database
                    mRef.child(it.surgery_id!!).setValue(updatedData)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Surgery Updated Successfully", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss() // Dismiss the dialog after successful update
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            delBtn.setOnClickListener {
                val mAuth = FirebaseAuth.getInstance()
                val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Operations")

                // Ensure data is not null before attempting deletion
                data?.let {
                    // Remove the medicine data from the database based on its unique ID
                    mRef.child(it.surgery_id!!).removeValue()
                        .addOnSuccessListener {
                            Toast.makeText(context, "Surgery Deleted Successfully", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss() // Dismiss the dialog after successful deletion
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }

        }
    }
