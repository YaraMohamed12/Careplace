package com.example.careplace

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

class UserAdapter(private val context: Context, private val userList: ArrayList<Contact_info_Data>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.bind(currentUser)
        holder.itemView.setOnClickListener {
            val myIntent = Intent(context, ChatActivity::class.java).apply {
                putExtra("name", currentUser.Name)
                putExtra("UID", currentUser.UID)
            }
            context.startActivity(myIntent)
        }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName: TextView = itemView.findViewById(R.id.name_txt1)
        private val userphoto: ImageView = itemView.findViewById(R.id.user_chatphoto)

        fun bind(user: Contact_info_Data) {
            textName.text = user.Name
            if(!user.profileImageUrl.isNullOrEmpty()) {
                Glide.with(itemView.context).load(user.profileImageUrl).into(userphoto)
            }



        }
    }
}

