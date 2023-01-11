package com.axyz.upasthithguru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.axyz.upasthithguru.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val signup = findViewById<TextView>(R.id.text_signup)
        signup.setOnClickListener {
            navigateToRegisterActivity()
        }
        val click_login = findViewById<Button>(R.id.click_login)
        click_login.setOnClickListener {

        }
        val forgot_pass = findViewById<TextView>(R.id.forgot_pass)
        forgot_pass.setOnClickListener {
            navigateToUpdatePasswordActivity()
        }
    }

    private fun navigateToRegisterActivity(){
        startActivity(Intent(applicationContext, RegisterActivity::class.java))
        finish()
    }
    private fun navigateToUpdatePasswordActivity(){
        startActivity(Intent(applicationContext, UpdatePasswordActivity::class.java))
        finish()
    }
}