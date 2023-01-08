package com.axyz.upasthithguru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@Serializable
data class CharacteristicDataClass(val attendance: Boolean)

@Serializable
data class CharacteristicDataBroadcast(val RollNo: String?)

const val CustomCharUuid = "ff82240a-27c1-4661-a15a-be5a22a17256"
const val CustomServiceUuidPrefix = "f4f5f6f9"
var permissionDialogShown = 0


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startAttendanceBtn = findViewById<Button>(R.id.start_attendance)
        val signupBtn = findViewById<Button>(R.id.signup)
        val loginBtn = findViewById<Button>(R.id.login)
        startAttendanceBtn.setOnClickListener {
//            val intent = Intent(this, StartAttendance::class.java)
//            startActivity(intent)
        }
        loginBtn.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        signupBtn.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.onRequestPermissionsResult(this, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        Log.d("RESUME", "On Resume Called --- $permissionDialogShown")
        if (!PermissionUtils.hasPermissions(this) && permissionDialogShown < 1) {
            PermissionUtils.requestPermissions(this)
            Log.d("Permissions -> ", "All Not Permission Given -> ")
        } else {
            Log.d("Permissions -> ", "All Permission Given")
        }
    }
}


