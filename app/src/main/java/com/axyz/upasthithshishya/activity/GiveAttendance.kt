package com.axyz.upasthithshishya.activity


import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.axyz.upasthithshishya.*
import com.axyz.upasthithshishya.databinding.ActivityGiveAttendanceBinding
import com.axyz.upasthithshishya.databinding.DialogEnterPinBinding
import com.axyz.upasthithshishya.other.CameraPreview

class GiveAttendance : AppCompatActivity() {
    private lateinit var binding: ActivityGiveAttendanceBinding
    private lateinit var sendPinButton: AppCompatButton
    private lateinit var cameraPreview: CameraPreview
    private lateinit var cameraPreviewContainer: FrameLayout
    private lateinit var turnOnCameraButton: Button
    private lateinit var startAttendanceButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGiveAttendanceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        sendPinButton = binding.giveAttendanceSendPinButton
        sendPinButton.setOnClickListener{
            val intent = Intent(this,MarkAttendence::class.java)
            startActivity(intent)
        }
//        startAttendanceButton = findViewById(R.id.startAttendanceButton)
//        cameraPreviewContainer = findViewById(R.id.cameraPreviewContainer)
//        turnOnCameraButton = findViewById(R.id.turnOnCameraButton)
//
//        turnOnCameraButton.setOnClickListener {
//            // Open the front camera when the button is clicked
//            turnOnCameraButton.visibility = View.INVISIBLE
//            openFrontCamera()
//        }
//
//        startAttendanceButton.setOnClickListener {
//            openPinDialogBox()
//        }
//
//    }
//
//    private fun openFrontCamera() {
//        val camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
//        camera.setDisplayOrientation(90)
//        cameraPreview = CameraPreview(this, camera)
//        cameraPreviewContainer.addView(cameraPreview)
//    }
//
//    private fun openPinDialogBox() {
//        val enterPinDialog = Dialog(this)
//        enterPinDialog.setContentView(R.layout.dialog_enter_pin)
//
//        enterPinDialog.setTitle("Enter your PIN")
//        enterPinDialog.setCancelable(true)
//
//        val confirmButton = enterPinDialog.findViewById<Button>(R.id.okButton)
//        confirmButton.setOnClickListener {
//            val errorMessageTextView = enterPinDialog.findViewById<TextView>(R.id.showWrongPinText)
//            val pinEditText = enterPinDialog.findViewById<EditText>(R.id.pinEditText)
//            val pin = pinEditText.text.toString()
//            if (pin.length == 4) {
//                if (pin == "1234") {
//                    enterPinDialog.dismiss()
//                    finish()
//                    val intent = Intent(this, MarkAttendence::class.java)
//                    startActivity(intent)
//                } else {
//                    errorMessageTextView.text = "Incorrect PIN. Please try again."
//                    errorMessageTextView.visibility = View.VISIBLE
//                }
//            } else {
//                errorMessageTextView.text = "PIN must be 4 digits. Please try again."
//                errorMessageTextView.visibility = View.VISIBLE
//            }
//        }
//
//        val cancelButton = enterPinDialog.findViewById<Button>(R.id.cancelButton)
//        cancelButton.setOnClickListener {
//            enterPinDialog.dismiss()
//        }
//
//        enterPinDialog.show()
        }
//
//
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