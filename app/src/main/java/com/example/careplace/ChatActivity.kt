package com.example.careplace

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    lateinit var ChatRecyclerView : RecyclerView
    lateinit var Send_btn : ImageView
    lateinit var messageeBox : EditText
    lateinit var messageAdapter: MessageAdapter
    lateinit var messageList : ArrayList<Message>
    var receiveRoom : String ?= null
    var senderRoom : String ?= null
    lateinit var mRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        Iniliaztion()



        val name = intent.getStringExtra("name")
        val RecevierUID = intent.getStringExtra("UID")
        val SenderUID = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = RecevierUID + SenderUID
        receiveRoom = SenderUID + RecevierUID
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        supportActionBar?.title = name
        // logic for add data to RecyclView

        ChatRecyclerView.layoutManager =LinearLayoutManager(this)
        ChatRecyclerView.adapter = messageAdapter

        mRef.child("Chat").child(senderRoom!!).child("messages").
        addValueEventListener(object  : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (postSnapshot in snapshot.children) {
                    val message1 = postSnapshot.getValue(Message::class.java)
                    messageList.add(message1!!)

                }
                messageAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        //add message to database
        Send_btn.setOnClickListener {
            val message = messageeBox.text.toString()
            val messageObject = Message(message,SenderUID)
            mRef.child("Chat")
                .child(senderRoom!!)
                .child("messages")
                .push().setValue(messageObject).addOnSuccessListener {

                    mRef.child("Chat")
                        .child(receiveRoom!!)
                        .child("messages")
                        .push().setValue(messageObject)
                }
            messageeBox.setText("")
        }

    }
    fun Iniliaztion()
    {
        ChatRecyclerView = findViewById(R.id.chatRecyclview)
        Send_btn =findViewById(R.id.sending_png)
        messageeBox = findViewById(R.id.messagebox)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        mRef = FirebaseDatabase.getInstance().getReference()


    }
}

