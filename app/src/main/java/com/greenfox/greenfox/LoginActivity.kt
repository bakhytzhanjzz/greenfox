package com.greenfox.greenfox

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class LoginActivity : AppCompatActivity() {

    private lateinit var etPhoneNumber: EditText
    private lateinit var cbTerms: CheckBox
    private lateinit var btnContinue: Button
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        setupListeners()
        updateContinueButtonState()
    }

    private fun initViews() {
        etPhoneNumber = findViewById(R.id.et_phone_number)
        cbTerms = findViewById(R.id.cb_terms)
        btnContinue = findViewById(R.id.btn_continue)
        btnBack = findViewById(R.id.btn_back)
    }

    private fun setupListeners() {
        // Phone number text watcher
        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateContinueButtonState()
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let { formatPhoneNumber(it) }
            }
        })

        // Terms checkbox listener
        cbTerms.setOnCheckedChangeListener { _, _ ->
            updateContinueButtonState()
        }

        // Back button listener
        btnBack.setOnClickListener {
            onBackPressed()
        }

        // Continue button listener
        btnContinue.setOnClickListener {
            if (isFormValid()) {
                proceedToVerification()
            }
        }
    }

    private fun updateContinueButtonState() {
        val isValid = isFormValid()
        btnContinue.isEnabled = isValid

        // Update text color based on state
        val textColor = if (isValid) {
            ContextCompat.getColor(this, android.R.color.white)
        } else {
            ContextCompat.getColor(this, android.R.color.darker_gray)
        }
        btnContinue.setTextColor(textColor)
    }

    private fun isFormValid(): Boolean {
        val phoneNumber = etPhoneNumber.text.toString().trim()
        val isPhoneValid = isValidPhoneNumber(phoneNumber)
        val isTermsAccepted = cbTerms.isChecked

        return isPhoneValid && isTermsAccepted
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Remove all non-digit characters
        val digitsOnly = phoneNumber.replace("\\D".toRegex(), "")

        // Kazakhstan phone numbers should have 10 digits after country code
        // Format: +7 XXX XXX XX XX (10 digits)
        return digitsOnly.length >= 10
    }

    private fun formatPhoneNumber(editable: Editable) {
        val input = editable.toString().replace("\\D".toRegex(), "")
        val formatted = StringBuilder()

        for (i in input.indices) {
            if (i >= 10) break

            if (i == 3 || i == 6) {
                formatted.append(" ")
            }
            if (i == 8) {
                formatted.append(" ")
            }
            formatted.append(input[i])
        }

        // Prevent infinite loop
        val formattedString = formatted.toString()
        if (editable.toString() != formattedString) {
            editable.replace(0, editable.length, formattedString)
        }
    }

    private fun proceedToVerification() {
        val phoneNumber = etPhoneNumber.text.toString().trim()
        val fullPhoneNumber = "+7${phoneNumber.replace("\\D".toRegex(), "")}"

        val intent = Intent(this, VerificationActivity::class.java)
        intent.putExtra("user_type", intent.getStringExtra("USER_TYPE"))
        intent.putExtra("phone_number", fullPhoneNumber)
        startActivity(intent)


        Log.d("LoginActivity", "Proceeding with phone: $fullPhoneNumber")

    }
}