package com.greenfox.greenfox

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ExploreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        Toast.makeText(this, "Добро пожаловать, клиент!", Toast.LENGTH_SHORT).show()
    }
}
