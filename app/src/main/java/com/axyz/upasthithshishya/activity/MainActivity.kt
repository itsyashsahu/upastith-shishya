package com.axyz.upasthithshishya.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.app
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.mongodb.User

var permissionDialogShown = 0


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUser: User? = app.currentUser
        if (currentUser != null && currentUser.loggedIn) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }
    }
}


