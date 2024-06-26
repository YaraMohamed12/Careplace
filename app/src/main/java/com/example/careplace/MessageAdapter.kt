package com.example.careplace

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context : Context , val messageList : ArrayList<Message>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
   val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1)
        {
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            return ReceiveViewHolder(view)
        }
        else
        {
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            return SendViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
       return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder.javaClass == SendViewHolder ::class.java)
        {
            //do stuff for sent

            val viewHolder = holder as SendViewHolder
            holder.sentmessage.text = currentMessage.message
        }
        else
        {

        val viewHolder = holder as ReceiveViewHolder
            holder.Receviemessage.text =currentMessage.message
        }
    }
    class SendViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView)
    {
    val sentmessage = itemView.findViewById<TextView>(R.id.sent_txt_message)
    }
    class ReceiveViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView)
    {
        val Receviemessage = itemView.findViewById<TextView>(R.id.receive_txt_message)
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId))
        {
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }
}