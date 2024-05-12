package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class Patient_Home_Screen : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var user_image: ImageView
    private lateinit var patname: TextView
    private lateinit var database: FirebaseDatabase
    private lateinit var mref: DatabaseReference
    private lateinit var myathun: FirebaseAuth
    private var Appuser: FirebaseUser? = null
    lateinit var Goto_chat : ImageView
    lateinit var select_doc : FrameLayout
    lateinit var select_Mdeicine : FrameLayout
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var your_profile_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView
    private val GALLERY_REQUEST_CODE = 123
    lateinit var updimg_img : ImageView
    lateinit var view : View
    lateinit var select_form : FrameLayout


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_home_screen)
        patname = findViewById(R.id.textView13)
        select_doc = findViewById(R.id.choose_doctor)
        select_Mdeicine = findViewById(R.id.choose_medicine)
        toolbar = findViewById(R.id.mytoolbar)
        toolbar.inflateMenu(R.menu.main_menu)
        select_form = findViewById(R.id.choose_form)

        ImportUserData()
        menuInflate()
        inilaztionlistenr()
        clicking()
        imageupload()
        RloaduserImage()
        val myreadata = ReadData()
        mref.addValueEventListener(myreadata)
    }


    private fun inilaztionlistenr()
    {
        Goto_chat = findViewById(R.id.goto_chat)
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
      your_profile_btn = findViewById(R.id.profile_icon_bar)
        calender_btn = findViewById(R.id.calender_icon_bar)
        user_image = findViewById(R.id.select_image)
    }

    private fun clicking()
    {
        Goto_chat.setOnClickListener {
            val intent = Intent(this,ContactActivity::class.java)
            startActivity(intent)
        }

        select_doc.setOnClickListener {
            val my_intent = Intent(this,Choose_your_doc::class.java)
            startActivity(my_intent)
        }
        select_Mdeicine.setOnClickListener {
            val my_intent10 = Intent(this,Medicine_List::class.java)
            startActivity(my_intent10)
        }
        home_btn.setOnClickListener {
            val myintent1 = Intent(this , Patient_Home_Screen::class.java)
            startActivity(myintent1)
        }

        setting_btn.setOnClickListener {
            val myintent2 = Intent(this ,Patient_Setting_Screen ::class.java)
            startActivity(myintent2)
        }

        your_profile_btn.setOnClickListener {
            val myintent3 = Intent(this ,My_Profile_Details_for_patient::class.java)
            startActivity(myintent3)
        }

        calender_btn.setOnClickListener {

            val myintent4 = Intent(this, Patient_Calender_Screen::class.java)
            startActivity(myintent4)

        }
        select_form.setOnClickListener{
            val intent5 = Intent(this, Medical_History_For_Patient_View::class.java)
            startActivity(intent5)
        }

    }

    private fun menuInflate() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.Log_out) {
                FirebaseAuth.getInstance().signOut()
                val myIntent = Intent(this@Patient_Home_Screen, DocOrPat::class.java)
                startActivity(myIntent)
                finish()
                true
            } else {
                false
            }
        }
    }

    private fun ImportUserData() {
        database = FirebaseDatabase.getInstance()
        myathun = FirebaseAuth.getInstance()
        Appuser = myathun.currentUser
        if (Appuser != null)  {
            mref = database.getReference("user/${myathun.currentUser!!.uid}/name")


        }
    }

    inner class ReadData : ValueEventListener {
        // export userdata from database
        override fun onDataChange(snapshot: DataSnapshot) {
            val username = snapshot.getValue(String::class.java)
            patname.text = username
        }
        override fun onCancelled(error: DatabaseError) {
            // Handle onCancelled
        }
    }
    @SuppressLint("MissingInflatedId")
    private fun imageupload() {
        user_image.setOnClickListener {
             view = layoutInflater.inflate(R.layout.setimage, null)
            val myDialogBuilder = AlertDialog.Builder(this)
            myDialogBuilder.setView(view)
            val alertDialog = myDialogBuilder.create()
            alertDialog.show()
            val updimg_btn = view.findViewById<Button>(R.id.upd_img_pho)
             updimg_img = view.findViewById<ImageView>(R.id.user_upd_img)
            updimg_btn.setOnClickListener {
                // Open gallery to select an image
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)



            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val storageRef = FirebaseStorage.getInstance().reference
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            val photoRef: StorageReference = storageRef.child("users_photos").child(selectedImageUri?.lastPathSegment!!)
            photoRef.putFile(selectedImageUri)
                .addOnSuccessListener {

                    photoRef.downloadUrl.addOnSuccessListener { downloadUri ->
                               Glide.with(this).load(downloadUri).into(updimg_img)

                        currentUser?.let { user ->
                            val userRef = FirebaseDatabase.getInstance().getReference("user/${user.uid}")
                            userRef.child("profileImageUrl").setValue(downloadUri.toString())
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Upload", Toast.LENGTH_SHORT).show()
                }
        }
    }
    fun RloaduserImage()
    {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val userRef = FirebaseDatabase.getInstance().getReference("user/${user.uid}")
            userRef.child("profileImageUrl").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val profileImageUrl = snapshot.getValue(String::class.java)
                    if (!profileImageUrl.isNullOrEmpty()) {
                        Glide.with(this@Patient_Home_Screen).load(profileImageUrl).into(user_image)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Patient_Home_Screen, "Failed to read user profile image URL", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}
