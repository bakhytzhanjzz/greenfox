package com.greenfox.greenfox

import LoginActivity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class ChooseUserActivity : AppCompatActivity() {

    private lateinit var relaxOption: RelativeLayout
    private lateinit var hostOption: RelativeLayout
    private lateinit var btnNext: Button

    private var selectedUserType: UserType? = null

    enum class UserType {
        CLIENT, PARTNER
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)

        relaxOption = findViewById(R.id.relax_option)
        hostOption = findViewById(R.id.host_option)
        btnNext = findViewById(R.id.btn_next)

        relaxOption.setOnClickListener {
            selectUserType(UserType.CLIENT)
        }

        hostOption.setOnClickListener {
            selectUserType(UserType.PARTNER)
        }

        btnNext.setOnClickListener {
            selectedUserType?.let { type ->
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("USER_TYPE", type.name)
                startActivity(intent)
            }
        }
    }

    private fun selectUserType(type: UserType) {
        selectedUserType = type
        btnNext.isEnabled = true
        btnNext.setTextColor(Color.WHITE)

        relaxOption.alpha = if (type == UserType.CLIENT) 1.0f else 0.5f
        hostOption.alpha = if (type == UserType.PARTNER) 1.0f else 0.5f
    }
}
