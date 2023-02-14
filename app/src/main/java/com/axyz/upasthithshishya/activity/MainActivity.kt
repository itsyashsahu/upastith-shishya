package com.axyz.upasthithshishya.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.activity.HomeActivity
import com.axyz.upasthithshishya.activity.OnboardingActivity
import com.axyz.upasthithshishya.other.CheckLogin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

var permissionDialogShown = 0


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(CheckLogin(this)){
            Toast.makeText(this,"User Already Signed In", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }else{
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }
    }
}


