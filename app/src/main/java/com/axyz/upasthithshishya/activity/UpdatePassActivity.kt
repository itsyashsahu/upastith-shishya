package com.axyz.upasthithshishya.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.axyz.upasthithshishya.R

class UpdatePassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pass)

        val verify_request = findViewById<AppCompatButton>(R.id.btn_changePassword)
//        verify_request.setOnClickListener {
//            startActivity(Intent(this, UpdatePassActivity::class.java))
//        }
    }
}