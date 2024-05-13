package com.example.careplace

import android.annotation.SuppressLint
import android.content.Context
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
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Doc_Adapter(context : Context, DocFormList : ArrayList <Doc_class_Data>)
    : ArrayAdapter<Doc_class_Data>(context ,0 ,DocFormList ) {
    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.form_images, parent,false)
        val data: Doc_class_Data? = getItem(position)
        val Doc_name : TextView = view.findViewById(R.id.form_name_card)
        val Doc_image : ImageView = view.findViewById(R.id.image_form_card)
        val Remove_btn : Button = view.findViewById(R.id.del_form_btn)
        Doc_name.text = data?.Doc_name
        val Doc_uri = data?.Doc_uri
        val Doc_id = data?.Doc_id
        Glide.with(context).load(Doc_uri).into(Doc_image)
        Doc_image.setOnClickListener {
            showUpdateDialog(data)
        }
        Remove_btn.setOnClickListener {
            val mAuth = FirebaseAuth.getInstance()
            val mRef = FirebaseDatabase.getInstance().getReference("/user/${mAuth.currentUser?.uid}/user_rays")
            data?.let {
                mRef.child(it.Doc_id!!).removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(context, "Surgery Deleted Successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        return view
    }
    @SuppressLint("MissingInflatedId")
    private fun showUpdateDialog(data: Doc_class_Data?) {
        // Inflate the custom dialog layout for image preview
        val dialogView = LayoutInflater.from(context).inflate(R.layout.image_preview_dailog, null)

        // Build and show the AlertDialog
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        // Find views within the dialog layout
        val docPreview: ImageView = dialogView.findViewById(R.id.Image_preview)

        // Load image using Glide into ImageView within the dialog
        val docUri = data?.Doc_uri
        Glide.with(context).load(docUri).into(docPreview)
    }
}