package com.greenfox.greenfox

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PartnerDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner_dashboard)

        Toast.makeText(this, "Добро пожаловать, партнёр!", Toast.LENGTH_SHORT).show()
    }
}
