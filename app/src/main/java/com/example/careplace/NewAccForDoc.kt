package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class NewAccForDoc : AppCompatActivity() {
    lateinit var back_btn : ImageView
    lateinit var toDropDownMenu : AutoCompleteTextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_acc_for_doc)
        back_btn =findViewById(R.id.backbtndoc)
        toDropDownMenu = findViewById(R.id.editxtSpecialization)
        back_btn.setOnClickListener {
            val myIntent3 = Intent(this ,DocOrPat::class.java)
            startActivity(myIntent3)
        }
        menupopulater()
    }
    private fun menupopulater()
    {

        val listDoctor = listOf("Cardiologist","Pulmonologist","orthopaedic","Ophthalmologist","Dentist",
            "Audiologist","ENT Specialists")
        val adapter = ArrayAdapter(this,R.layout.drop_down_menu,listDoctor)
        toDropDownMenu.setAdapter(adapter)


    }
}