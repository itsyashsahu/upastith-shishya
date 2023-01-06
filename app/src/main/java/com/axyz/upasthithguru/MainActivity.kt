package com.axyz.upasthithguru

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import kotlinx.serialization.Serializable

@Serializable
data class CharacteristicDataClass(val attendance: Boolean)

@Serializable
data class CharacteristicDataBroadcast(val RollNo: String?)

const val CustomCharUuid = "ff82240a-27c1-4661-a15a-be5a22a17256"
const val CustomServiceUuidPrefix = "f4f5f6f9"


public var permissionDialogShown = 0
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startAttendanceBtn = findViewById<Button>(R.id.start_attendance)
        startAttendanceBtn.setOnClickListener {
//            val intent = Intent(this, StartAttendance::class.java)
//            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.onRequestPermissionsResult(this,requestCode, permissions, grantResults)
    }
    override fun onResume() {
        super.onResume()
        Log.d("RESUME", "On Resume Called --- $permissionDialogShown")
        if (!PermissionUtils.hasPermissions(this) && permissionDialogShown<1) {
            PermissionUtils.requestPermissions(this)
            Log.d("Permissions -> ","All Not Permission Given -> ")
        }else{
            Log.d("Permissions -> ","All Permission Given")
        }
    }
}


