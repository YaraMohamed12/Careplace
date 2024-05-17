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

class Chronic_Diseases_Adapter (context : Context, chronicDiseasesFormList: ArrayList <Chronic_Diseases_Data>)
    : ArrayAdapter<Chronic_Diseases_Data>(context ,0 ,chronicDiseasesFormList ) {

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.simple_list_card, parent,false)
        val data: Chronic_Diseases_Data? = getItem(position)
        val disease_name : TextView = view.findViewById(R.id.operation_name_text1)
        val Uptbtn : Button = view.findViewById(R.id.editt_btn)
//        val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
//        val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Chronic_Diseases")
        disease_name.text = data?.illness_name.toString()

        Uptbtn.setOnClickListener {
            showUpdateDialog(data)
        }

        return view
    }

    @SuppressLint("MissingInflatedId")
    private fun showUpdateDialog(data: Chronic_Diseases_Data?) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.simple_card_update, null)
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        val name: EditText = dialogView.findViewById(R.id.operation_name_editt)
        val updBtn: Button = dialogView.findViewById(R.id.update)
        val delBtn: Button = dialogView.findViewById(R.id.remove)
        name.setText(data!!.illness_name)

        updBtn.setOnClickListener {
            val mAuth = FirebaseAuth.getInstance()
            val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}")

            // Ensure data is not null before attempting update
            data?.let {
                val newName = name.text.toString().trim()

                // Update the medicine data with the new values
                val updatedData = Chronic_Diseases_Data( newName ,it.illness_id)

                // Perform the update in the database
                mRef.child("Chronic Diseases").child(it.illness_id!!).setValue(updatedData)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Chronic disease Updated Successfully", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss() // Dismiss the dialog after successful update
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        delBtn.setOnClickListener {
            val mAuth = FirebaseAuth.getInstance()
            val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Chronic Diseases")

            // Ensure data is not null before attempting deletion
            data?.let {
                // Remove the medicine data from the database based on its unique ID
                mRef.child(it.illness_id!!).removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(context, "Chronic disease Deleted Successfully", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss() // Dismiss the dialog after successful deletion
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }
}