package com.example.careplace

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Medicine_List : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_list)
        val my_flotbtn = findViewById<FloatingActionButton>(R.id.myFloatButton)
        my_flotbtn.setOnClickListener {
            val myDialogBuldier = AlertDialog.Builder(this)
            val veiw =  layoutInflater.inflate(R.layout.medicine_dialog,null)
            myDialogBuldier.setView(veiw)
            val alertDialogbox = myDialogBuldier.create()
            alertDialogbox.show()

        }

    }
}