package com.axyz.upasthithshishya.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.axyz.upasthithshishya.R

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val request_change = findViewById<AppCompatButton>(R.id.btn_send)
        request_change.setOnClickListener {
            startActivity(Intent(this, VerifyPassChangeActivity::class.java))
        }
    }
}