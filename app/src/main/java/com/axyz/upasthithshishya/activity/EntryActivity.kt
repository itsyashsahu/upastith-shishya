package com.axyz.upasthithshishya.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.axyz.upasthithshishya.R

//import com.axyz.upasthithshishya.databinding.ActivityMainBinding


class EntryActivity : AppCompatActivity() {
    //    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        val btn_register = findViewById<Button>(R.id.btn_register)
        btn_register.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        val btn_login = findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }
}