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

class Medication_Form_Adapter (context : Context, medicationFormList : ArrayList <Medicatin_Form_Data>)
    : ArrayAdapter <Medicatin_Form_Data>(context ,0 ,medicationFormList ) {
        override fun getView( position: Int, convertView: View?, parent: ViewGroup): View {
            val view = LayoutInflater.from(context).inflate(R.layout.medication_and_surgeries_card, parent,false)
            val data: Medicatin_Form_Data? = getItem (position)
            val medicin_name: TextView = view.findViewById(R.id.name)
            val medicin_details: TextView = view.findViewById(R.id.date)
            val Uptbtn : Button = view.findViewById(R.id.edit_btn)
            val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
            val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Medication")
            medicin_name.text = data?.medicin_name.toString()
            medicin_details.text = data?.medicin_date.toString()

            Uptbtn.setOnClickListener {
                showUpdateDialog(data)
            }

            return view
        }

        @SuppressLint("MissingInflatedId")
        private fun showUpdateDialog(data: Medicatin_Form_Data?) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.medication_and_surgeries_dialog, null)
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setView(dialogView)
            val alertDialog = dialogBuilder.create()
            alertDialog.show()
            val name: EditText = dialogView.findViewById(R.id.name1)
            val details_op: EditText = dialogView.findViewById(R.id.date1)
            val updBtn: Button = dialogView.findViewById(R.id.upd_btn)
            val delBtn: Button = dialogView.findViewById(R.id.remove_btn)
            name.setText(data!!.medicin_name)
            details_op.setText(data.medicin_date)

            updBtn.setOnClickListener {
                val mAuth = FirebaseAuth.getInstance()
                val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Medication")
                // Ensure data is not null before attempting update
                data?.let {
                    val newName = name.text.toString().trim()
                    val new_detail = details_op.text.toString().trim()

                    // Update the medicine data with the new values
                    val updatedData = MedicineData( newName, new_detail,it.medicin_id)

                    // Perform the update in the database
                    mRef.child(it.medicin_id!!).setValue(updatedData)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Medicine Updated Successfully", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss() // Dismiss the dialog after successful update
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            delBtn.setOnClickListener {
                val mAuth = FirebaseAuth.getInstance()
                val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Medication")

                // Ensure data is not null before attempting deletion
                data?.let {
                    // Remove the medicine data from the database based on its unique ID
                    mRef.child(it.medicin_id!!).removeValue()
                        .addOnSuccessListener {
                            Toast.makeText(context, "Medicine Deleted Successfully", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss() // Dismiss the dialog after successful deletion
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }

        }
}