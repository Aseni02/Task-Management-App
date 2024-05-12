package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Start a coroutine on the Main thread
        CoroutineScope(Dispatchers.Main).launch {
            // Delay execution for 2 seconds
            delay(2000)
            // Start MainActivity and finish this SplashActivity
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}
