package com.axyz.upasthithguru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val alreadyhaveaccount = findViewById<TextView>(R.id.alreadyhaveaccount)
        alreadyhaveaccount.setOnClickListener {
            navigateToLoginActivity()
        }

        val click_register = findViewById<Button>(R.id.click_register)
        click_register.setOnClickListener {

        }
    }


    private fun navigateToLoginActivity(){
        startActivity(Intent(applicationContext,LoginActivity::class.java))
        finish()
    }
}