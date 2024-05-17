package com.example.careplace

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class My_Profile_Details_for_doc : AppCompatActivity() {
    lateinit var user_image: ImageView
    lateinit var user_name: TextView
    lateinit var user_gender: TextView
    lateinit var user_age: TextView
    lateinit var user_spec: TextView
    lateinit var user_loc: TextView
    lateinit var user_phone: TextView
    lateinit var user_Notional_id: TextView
    lateinit var user_bdate: TextView
    lateinit var user_email: TextView
    lateinit var mRef1: DatabaseReference
    lateinit var mAuthn: FirebaseAuth
    lateinit var update_btn : FloatingActionButton
    private val GALLERY_REQUEST_CODE = 123
    lateinit var user2_image : ImageView
    lateinit var home_btn : ImageView
    lateinit var setting_btn : ImageView
    lateinit var calender_btn : ImageView
    lateinit var chat_btn : ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_details_for_doc)
        mRef1 = FirebaseDatabase.getInstance().getReference("DUser")
        mAuthn = FirebaseAuth.getInstance()
        inialzation()
        setuplisenter()
        RetriveUserDate()
        UpdateUserData()



    }
    fun inialzation(){
        home_btn = findViewById(R.id.home_icon)
        setting_btn = findViewById(R.id.setting_icon)
        calender_btn = findViewById(R.id.calender_icon_bar)
        chat_btn  = findViewById(R.id.goto_chat)
        user_name = findViewById(R.id.name_data_doc)
        user_age = findViewById(R.id.age_data_doc)
        user_gender = findViewById(R.id.gender_data_doc)
        user_spec = findViewById(R.id.Specli_data_doc)
        user_loc = findViewById(R.id.loc_data)
        user_email = findViewById(R.id.My_email_doc)
        user_phone = findViewById(R.id.Phone_num_doc)
        user_Notional_id = findViewById(R.id.id_data_doc)
        user_bdate = findViewById(R.id.birth_data_doc)
        user_image = findViewById(R.id.prof_pic2_doc)
        update_btn = findViewById(R.id.flobtn_user_doc)

    }
    fun setuplisenter()
    {
        home_btn.setOnClickListener {
            val myintent1 = Intent(this , Doctor_Home_Screen::class.java)
            startActivity(myintent1)
        }
        setting_btn.setOnClickListener {
            val myintent2 = Intent(this ,Doctor_Setting_Screen ::class.java)
            startActivity(myintent2)
        }
        calender_btn.setOnClickListener {
            val myintent4 = Intent(this , Doctor_Calender_Screen::class.java)
            startActivity(myintent4)
        }
        chat_btn.setOnClickListener {
            val myintent5 = Intent(this ,ContactActivity_For_Patient ::class.java)
            startActivity(myintent5)
        }
        }

    private fun RetriveUserDate() {
        mAuthn.currentUser?.uid?.let { userId ->
            mRef1.child(userId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val P_name = snapshot.child("dname").getValue(String::class.java)
                    val P_age = snapshot.child("dage").getValue(String::class.java)
                    val P_loc = snapshot.child("Location").getValue(String::class.java)
                    val P_Spec = snapshot.child("specialization").getValue(String::class.java)
                    val P_phone = snapshot.child("dphone").getValue(String::class.java)
                    val P_bdate = snapshot.child("dbdate").getValue(String::class.java)
                    val P_nId = snapshot.child("dnationid").getValue(String::class.java)
                    val P_email = snapshot.child("demail").getValue(String::class.java)
                    val P_gender = snapshot.child("dgender").getValue(String::class.java)

                    user_name.text = P_name
                    user_spec.text = P_Spec
                    user_loc.text = P_loc
                    user_phone.text = P_phone
                    user_gender.text = P_gender
                    user_age.text = P_age
                    user_bdate.text = P_bdate
                    user_email.text = P_email
                    user_Notional_id.text = P_nId

                    val profileImageUrl = snapshot.child("profileImageUrl").getValue(String::class.java)
                    Glide.with(this@My_Profile_Details_for_doc).load(profileImageUrl).into(user_image)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if needed
                }
            })
        }
    }
    fun UpdateUserData()
    {
        val userId = mAuthn.currentUser?.uid

        update_btn.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.docprofile_dialog, null)
            val myDialogBuilder = AlertDialog.Builder(this)
            myDialogBuilder.setView(view)
            val alertDialog = myDialogBuilder.create()
            alertDialog.show()

            val sumbit_btn = view.findViewById<Button>(R.id.update_profile_doc)
            val name = view.findViewById<EditText>(R.id.edit_name_dialog_doc)
            val email = view.findViewById<EditText>(R.id.edit_email_dialog_doc)
            val age = view.findViewById<EditText>(R.id.edit_age_doc)
            val gender = view.findViewById<EditText>(R.id.edit_gender_doc)
            val phone = view.findViewById<EditText>(R.id.edit_phone_dialog_doc)
            var Location = view.findViewById<EditText>(R.id.edit_loc_dialog_doc)
            val Nationid = view.findViewById<EditText>(R.id.edit_Nid_doc)
            val bdate = view.findViewById<EditText>(R.id.edit_DOB_doc)
            user2_image = view.findViewById<ImageView>(R.id.prof_pic_doc)
            var  profileImageUrl : kotlin.String?= null

            user2_image.setOnClickListener {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)

            }


            // Fetch data from Firebase when dialog is shown
            mRef1.child(userId!!).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userData = snapshot.getValue(DUsers::class.java)
                    userData?.let {
                        name.setText(it.DName)
                        email.setText(it.DEmail)
                        age.setText(it.DAge)
                        gender.setText(it.DGender)
                        phone.setText(it.DPhone)
                        Nationid.setText(it.Dnationid)
                        bdate.setText(it.DBdate)

                        profileImageUrl = snapshot.child("profileImageUrl").getValue(String::class.java)
                        if (!isDestroyed && !isFinishing) {
                            Glide.with(applicationContext).load(profileImageUrl).into(user2_image)
                        }
                        val located = snapshot.child("Location").getValue(String::class.java)

                        if (located != null) {
                            Location.setText(located)
                        } else {
                            Location.setText("Location not available" )
                        }



                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@My_Profile_Details_for_doc, "There was a problem", Toast.LENGTH_SHORT).show()
                }
            })

            sumbit_btn.setOnClickListener {
                // Get updated data from EditText fields
                val name2 = name.text.toString()
                val email2 = email.text.toString()
                val age2 = age.text.toString()
                val Location2 = Location.text.toString()
                val phone2 = phone.text.toString()
                val gender2 = gender.text.toString()
                val bdate2 = bdate.text.toString()
                val nationId2 = Nationid.text.toString()

                mRef1.child(userId).child("dname").setValue(name2)
                mRef1.child(userId).child("dage").setValue(age2)
                mRef1.child(userId).child("demail").setValue(email2)
                mRef1.child(userId).child("dgender").setValue(gender2)
                mRef1.child(userId).child("dbdate").setValue(bdate2)
                mRef1.child(userId).child("dphone").setValue(phone2)
                mRef1.child(userId).child("Location").setValue(Location2)
                mRef1.child(userId).child("dnationid").setValue(nationId2)


                // Update profile image URL in Firebase
                profileImageUrl?.let {
                    mRef1.child(userId).child("profileImageUrl").setValue(it)
                        .addOnSuccessListener {
                            Toast.makeText(this@My_Profile_Details_for_doc, "All date is up to date", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@My_Profile_Details_for_doc, "Failed to update profile image URL", Toast.LENGTH_SHORT).show()
                        }
                }
                alertDialog.dismiss()
            }


        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val storageRef = FirebaseStorage.getInstance().reference
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            val photoRef: StorageReference = storageRef.child("Dusers_photos").child(selectedImageUri?.lastPathSegment!!)
            photoRef.putFile(selectedImageUri)
                .addOnSuccessListener {

                    photoRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        Glide.with(this).load(downloadUri).into(user2_image)
                        currentUser?.let { user ->
                            val userRef = FirebaseDatabase.getInstance().getReference("DUser/${user.uid}")
                            userRef.child("profileImageUrl").setValue(downloadUri.toString())
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Upload", Toast.LENGTH_SHORT).show()
                }
        }
    }
}