package com.example.careplace

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MedicineAdpater(context: Context, NoteList : ArrayList<MedicineData>)
    : ArrayAdapter<MedicineData>(context ,0 ,NoteList)  {
    @SuppressLint("MissingInflatedId", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =   LayoutInflater.from(context).inflate(R.layout.medicine_card,parent,false)
        val data: MedicineData? = getItem(position)
        val myname : TextView = view.findViewById(R.id.Medicine_name)
        val mytime : TextView = view.findViewById(R.id.timeofdoz)
        val mydoz : TextView = view.findViewById(R.id.Numberdoz)
        val delbtn : Button = view.findViewById(R.id.Remove_btn)
        val Uptbtn : Button = view.findViewById(R.id.Edit_btn)
        myname.text = data?.medicineName.toString()
        mytime.text = data?.dateForTaking.toString()
        mydoz.text = data?.numberOfDoses.toString()
        delbtn.setOnClickListener {
            Toast.makeText(context, "Succesful clicked", Toast.LENGTH_SHORT).show()
        }
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

    }

}


