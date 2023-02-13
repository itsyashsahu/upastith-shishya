package com.axyz.upasthithshishya


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class GiveAttendance : AppCompatActivity() {
    private lateinit var cameraPreview: CameraPreview
    private lateinit var cameraPreviewContainer: FrameLayout
    private lateinit var turnOnCameraButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_give_attendance)
        cameraPreviewContainer = findViewById(R.id.cameraPreviewContainer)
        turnOnCameraButton = findViewById(R.id.turnOnCameraButton)

        turnOnCameraButton.setOnClickListener {
            // Open the front camera when the button is clicked
            turnOnCameraButton.visibility = View.INVISIBLE
            openFrontCamera()
        }
    }

    private fun openFrontCamera() {
        val camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
        camera.setDisplayOrientation(90)
        cameraPreview = CameraPreview(this, camera)
        cameraPreviewContainer.addView(cameraPreview)
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