package com.greenfox.greenfox

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// ProfileActivity.kt
class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placeholder)

        findViewById<TextView>(R.id.tv_title).text = "Аккаунт"
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            onBackPressed()
        }
    }
}