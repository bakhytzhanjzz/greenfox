package com.greenfox.greenfox

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class VerificationActivity : AppCompatActivity() {

    private lateinit var otpFields: Array<EditText>
    private lateinit var btnContinue: Button
    private lateinit var btnBack: ImageButton
    private lateinit var tvTimer: TextView
    private lateinit var tvResendCode: TextView

    private var countDownTimer: CountDownTimer? = null
    private var phoneNumber: String? = null

    companion object {
        private const val TIMER_DURATION = 60000L // 60 seconds
        private const val OTP_LENGTH = 6
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        // Get phone number from intent
        phoneNumber = intent.getStringExtra("phone_number")

        initViews()
        setupOtpFields()
        setupListeners()
        startTimer()
        updateContinueButtonState()
    }

    private fun initViews() {
        otpFields = arrayOf(
            findViewById(R.id.et_code_1),
            findViewById(R.id.et_code_2),
            findViewById(R.id.et_code_3),
            findViewById(R.id.et_code_4),
            findViewById(R.id.et_code_5),
            findViewById(R.id.et_code_6)
        )

        btnContinue = findViewById(R.id.btn_continue)
        btnBack = findViewById(R.id.btn_back)
        tvTimer = findViewById(R.id.tv_timer)
        tvResendCode = findViewById(R.id.tv_resend_code)
    }

    private fun setupOtpFields() {
        otpFields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        // Move to next field
                        if (index < otpFields.size - 1) {
                            otpFields[index + 1].requestFocus()
                        }
                    }
                    updateContinueButtonState()
                    updateFieldBackground(index)
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            // Handle backspace
            editText.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (otpFields[index].text.toString().isEmpty() && index > 0) {
                        otpFields[index - 1].requestFocus()
                        otpFields[index - 1].setText("")
                    }
                }
                false
            }
        }
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { onBackPressed() }

        btnContinue.setOnClickListener {
            if (isOtpComplete()) {
                verifyOtp()
            }
        }

        tvResendCode.setOnClickListener {
            resendCode()
        }
    }

    private fun updateFieldBackground(index: Int) {
        val field = otpFields[index]
        val isFilled = field.text.toString().isNotEmpty()

        val backgroundRes = if (isFilled) {
            R.drawable.otp_input_filled
        } else {
            R.drawable.otp_input_selector
        }
        field.setBackgroundResource(backgroundRes)
    }

    private fun updateContinueButtonState() {
        val isComplete = isOtpComplete()
        btnContinue.isEnabled = isComplete

        // Update text color based on state
        val textColor = if (isComplete) {
            ContextCompat.getColor(this, android.R.color.white)
        } else {
            ContextCompat.getColor(this, android.R.color.darker_gray)
        }
        btnContinue.setTextColor(textColor)
    }

    private fun isOtpComplete(): Boolean {
        return otpFields.all { it.text.toString().trim().isNotEmpty() }
    }

    private fun getEnteredOtp(): String {
        return otpFields.joinToString("") { it.text.toString().trim() }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(TIMER_DURATION, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                tvTimer.text = String.format("0:%02d", seconds)
            }

            override fun onFinish() {
                tvTimer.visibility = View.GONE
                tvResendCode.visibility = View.VISIBLE
            }
        }
        countDownTimer?.start()
    }

    private fun resendCode() {
        // TODO: Implement resend code logic
        Log.d("VerificationActivity", "Resending code to: $phoneNumber")

        // Reset timer
        tvTimer.visibility = View.VISIBLE
        tvResendCode.visibility = View.GONE
        startTimer()

        // Clear OTP fields
        clearOtpFields()
    }

    private fun clearOtpFields() {
        otpFields.forEach { field ->
            field.setText("")
            field.setBackgroundResource(R.drawable.otp_input_selector)
        }
        otpFields[0].requestFocus()
        updateContinueButtonState()
    }

    private fun verifyOtp() {
        val enteredOtp = getEnteredOtp()
        val userType = intent.getStringExtra("user_type")

        Log.d("VerificationActivity", "Verifying OTP: $enteredOtp for userType: $userType")

        if (enteredOtp.length == OTP_LENGTH) {
            val nextIntent = when (userType) {
                "CLIENT" -> Intent(this, ExploreActivity::class.java)
                "PARTNER" -> Intent(this, PartnerDashboardActivity::class.java)
                else -> null
            }

            if (nextIntent != null) {
                nextIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(nextIntent)
                finish()
            } else {
                Log.e("VerificationActivity", "Unknown user type: $userType")
                showOtpError()
            }

        } else {
            showOtpError()
        }
    }


    private fun showOtpError() {
        // Shake animation or show error message
        otpFields.forEach { field ->
            field.setBackgroundResource(R.drawable.otp_input_error)
        }

        // Reset after a delay
        otpFields[0].postDelayed({
            clearOtpFields()
        }, 1500)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}