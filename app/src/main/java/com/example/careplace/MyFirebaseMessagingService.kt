package com.example.careplace

import com.google.firebase.messaging.FirebaseMessagingService

import android.util.Log

import com.google.firebase.messaging.RemoteMessage
import kotlin.String

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Check if message contains data payload
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        // Check if message contains notification payload
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

        // If you need to send the token to your server, do so here
        // Assuming you have implemented a method to send the token to your server
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        // Implement your logic to send the FCM token to your server here
        // This token is required to send notifications to this device
        // You may need to store this token on your server to target specific devices for notifications
        Log.d(TAG, "FCM Token: $token")
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}
