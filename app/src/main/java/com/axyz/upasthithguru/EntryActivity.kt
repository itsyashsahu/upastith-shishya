package com.axyz.upasthithguru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
//import com.axyz.upasthithguru.databinding.ActivityMainBinding


class EntryActivity : AppCompatActivity() {
//    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_entry)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        val btn_register = findViewById<Button>(R.id.btn_register)
        btn_register.setOnClickListener {
            navigateToRegisterActivity()
        }

        val btn_login = findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener {
            navigateToLoginActivity()
        }
    }

    private fun navigateToLoginActivity(){
        startActivity(Intent(applicationContext,LoginActivity::class.java))
        finish()
    }
    private fun navigateToRegisterActivity(){
        startActivity(Intent(applicationContext, RegisterActivity::class.java))
        finish()
    }
}