package com.example.careplace

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AlarmingScreen : AppCompatActivity() {
    private lateinit var stopButton: Button
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarming_screen)
        mediaPlayer = MediaPlayer.create(this, R.raw.notification_sound)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        stopButton = findViewById(R.id.stopsound)

        stopButton.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}