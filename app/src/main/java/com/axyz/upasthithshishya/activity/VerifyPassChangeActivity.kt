package com.axyz.upasthithshishya.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.axyz.upasthithshishya.R

class VerifyPassChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_pass_change)

        val verify_request = findViewById<AppCompatButton>(R.id.click_verify)
        verify_request.setOnClickListener {
            startActivity(Intent(this, UpdatePassActivity::class.java))
        }
    }
}