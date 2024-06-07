package com.example.careplace

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MedicineAdpater(context: Context, NoteList : ArrayList<MedicineData>)
    : ArrayAdapter<MedicineData>(context ,0 ,NoteList)  {
    @SuppressLint("MissingInflatedId", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =   LayoutInflater.from(context).inflate(R.layout.medicine_card,parent,false)
        val data: MedicineData? = getItem(position)
        val myname : TextView = view.findViewById(R.id.Medicine_name)
        val mytime : TextView = view.findViewById(R.id.timeofdoz)
        val mydoz : TextView = view.findViewById(R.id.Numberdoz)
        val Uptbtn : Button = view.findViewById(R.id.Edit_btn)
        val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
        val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Medicine")
        myname.text = data?.medicineName.toString()
        mytime.text = data?.dateForTaking.toString()
        mydoz.text = data?.numberOfDoses.toString()

        Uptbtn.setOnClickListener {
            showUpdateDialog(data)
        }

        return view
    }
    private fun showUpdateDialog(data: MedicineData?) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.medcineupdele, null)
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        val name: EditText = dialogView.findViewById(R.id.doz_name_dialog2)
        val time: EditText = dialogView.findViewById(R.id.doz_time_dialog2)
        val doz: EditText = dialogView.findViewById(R.id.doz_no_dialog2)
        val updBtn: Button = dialogView.findViewById(R.id.btnupdate)
        val delBtn: Button = dialogView.findViewById(R.id.btndelete)
        name.setText(data!!.medicineName)
        time.setText(data.dateForTaking)
        doz.setText(data.numberOfDoses)

        updBtn.setOnClickListener {
            val mAuth = FirebaseAuth.getInstance()
            val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Medicine")

            // Ensure data is not null before attempting update
            data?.let {
                val newName = name.text.toString().trim()
                val newTime = time.text.toString().trim()
                val newDose = doz.text.toString().trim()

                // Update the medicine data with the new values
                val updatedData = MedicineData( newName,newDose, newTime,it.medicineid)

                // Perform the update in the database
                mRef.child(it.medicineid!!).setValue(updatedData)
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
            val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/Medicine")

            // Ensure data is not null before attempting deletion
            data?.let {
                // Remove the medicine data from the database based on its unique ID
                mRef.child(it.medicineid!!).removeValue()
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


