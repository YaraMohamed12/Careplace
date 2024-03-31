package com.example.careplace

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(private val context: Context, private val userList: ArrayList<Users>) :
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
            val myintent = Intent(context , ChatActivity ::class.java)
            myintent.putExtra("name" , currentUser.Name)
            myintent.putExtra("UID" , currentUser.UID)
            context.startActivity(myintent)

        }


    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName: TextView = itemView.findViewById(R.id.name_txt1)

        fun bind(user: Users) {
            textName.text = user.Name
        }
    }
}
