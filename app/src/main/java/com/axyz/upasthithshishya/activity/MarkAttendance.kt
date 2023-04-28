package com.axyz.upasthithshishya.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.bluetooth.*
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.app
import com.axyz.upasthithshishya.courses.CourseInfo
import io.realm.kotlin.mongodb.ext.customDataAsBsonDocument
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

@Serializable
data class CharacteristicDataClass(val attendance: Boolean)

@Serializable
data class CharacteristicDataBroadcast(val e: String?)


val CustomCharUuid = "ff82240a-27c1-4661-a15a-be5a22a17256"
val CustomServiceUuidPrefix = "f4f5f6f9"

class MarkAttendence : AppCompatActivity() {

    private lateinit var bluetoothGattServer: BluetoothGattServer

    // Data Class for Characteristics Object That is written & Read
    lateinit var email: String
    lateinit var dialog: Dialog
    //    = intent.getStringExtra("Roll_no")

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = app.currentUser?.customDataAsBsonDocument()?.getValue("email")?.asString()?.value.toString()
        // Create a Dialog object and set its content view.
        dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_mark_attendance)

        // Set the dialog to not be dismissible when touched outside its window.
        dialog.setCanceledOnTouchOutside(false)
        val retryButton = dialog.findViewById<Button>(R.id.retryButton)
        retryButton.visibility = View.INVISIBLE

        // Define a Handler to post a delayed message
        val handler = Handler()
        handler.postDelayed({
            // Update the visibility of the retryButton to visible
            retryButton.visibility = View.VISIBLE
            retryButton.setOnClickListener {
                closeBluetoothGattServer(bluetoothGattServer)
                dialog.cancel()
                finish()
                startActivity(Intent(this, GiveAttendance::class.java))
            }
        }, 60*1000)

        // Show the dialog.
        dialog.show()

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            ?: // Device does not support Bluetooth
            return

        val REQUEST_ENABLE_BT = 123
        // Check if Bluetooth is enabled
        if (!bluetoothAdapter.isEnabled) {
            // Bluetooth is not enabled, request permission to enable it
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else {
            // Bluetooth is already enabled
            startGattSever(bluetoothAdapter)
        }


        // println("The Value of Roll-No -> $RollNo")
        // if (PermissionUtils.hasPermissions(this)) {
        // } else {
        // println("BroadCasting BLocked --------------------------------------------> ${!PermissionUtils.hasPermissions(this)}")
        // PermissionUtils.requestPermissions(this)
        // }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, do something
                val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    ?: // Device does not support Bluetooth
                    return
                startGattSever(bluetoothAdapter)
            } else {
//                Toast(this,this@StartAttendance,)
                Log.e(TAG, "User Denied to Turn on Bluetooth")
                // User did not enable Bluetooth, handle this case
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun startGattSever(bluetoothAdapter: BluetoothAdapter) {

        // Get the BluetoothLeAdvertiser
        val bluetoothLeAdvertiser = bluetoothAdapter.bluetoothLeAdvertiser

        // Check if Bluetooth LE advertising is supported on the device
        if (bluetoothLeAdvertiser == null) {
            Log.e(TAG,"---xxxxx---> Bluetooth LE advertising is not supported on this device")
            return
        }

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

        bluetoothGattServer =
            bluetoothManager.openGattServer(this, object : BluetoothGattServerCallback() {
                override fun onConnectionStateChange(
                    device: BluetoothDevice, status: Int, newState: Int
                ) {
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        Log.d(TAG,"------> Connected to the device - ${device.address}");
//                        println("Connected to the device - ${device.name ?: device.address}")
                    }
                }

                override fun onCharacteristicReadRequest(
                    device: BluetoothDevice,
                    requestId: Int,
                    offset: Int,
                    characteristic: BluetoothGattCharacteristic
                ) {
                    println("request recieved from the device $device")
                    Log.d(TAG,"------> Characteristic Read Request :: -> $device");
                    // Send the read response
//                    if (
                        bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, characteristic.value)
//                    ) {
//                        bluetoothGattServer.close()
//                        Log.e(TAG, "Error sending read response server closed")
//                    }
                }

                override fun onCharacteristicWriteRequest(
                    device: BluetoothDevice,
                    requestId: Int,
                    characteristic: BluetoothGattCharacteristic,
                    preparedWrite: Boolean,
                    responseNeeded: Boolean,
                    offset: Int,
                    value: ByteArray
                ) {
                    Log.d(TAG,"------> Characteristic Write Request :: -> $device");
                    val obj = Json.decodeFromString<CharacteristicDataClass>(
                        String(
                            value, Charsets.UTF_8
                        )
                    )
                    // add a coutom check to identify that the writer is our desired teacher only
                    // just for demo purpose
                    if (obj.attendance) {
                        characteristic.value = value
                        val service: BluetoothGattService =
                            bluetoothGattServer.getService(characteristic.service.uuid)
                        bluetoothGattServer.removeService(service)

                        // "Characteristics OverWritten with -> $value",
                        val handler = Handler(Looper.getMainLooper())
                        handler.post {
                            Toast.makeText(
                                this@MarkAttendence,
                                "Attendence Marked Successfully", Toast.LENGTH_LONG
                            ).show()
                        }
                        // Send the response back to the client
                        if (responseNeeded) {
                            val status = BluetoothGatt.GATT_SUCCESS
                            bluetoothGattServer.sendResponse(
                                device, requestId, status, offset, value
                            )
                        }
                        // attendence is marked our work is done
                        // close the gatt server
                        closeBluetoothGattServer(bluetoothGattServer)
                        // diable the bluetooth device
//                        bluetoothAdapter.disable()
                        // End this Activity
                        dialog.cancel()
                        finish()
                        val intent = Intent(this@MarkAttendence,CourseInfo::class.java)
                        startActivity(intent)
                    }
                }
            })
//


        val uuid = tryGeneratingUUIDWithPrefix(CustomServiceUuidPrefix)
        if (uuid == null) {
            // Show a toast to the user indicating that a valid UUID could not be generated
            Log.e(TAG,"Failed to generate a valid UUID with prefix");
            Toast.makeText(this, "Failed to generate a valid UUID with prefix", Toast.LENGTH_SHORT).show()
        }
        removeGattServicesWithPrefix(bluetoothGattServer, CustomServiceUuidPrefix)

        // Add the service to the server
        val service = BluetoothGattService(
            uuid, BluetoothGattService.SERVICE_TYPE_PRIMARY
        )

//        println("Characterist -> CoustomCharUuid  &&  ")
        val characteristic = BluetoothGattCharacteristic(
            UUID.fromString(CustomCharUuid),
            BluetoothGattCharacteristic.PROPERTY_READ or BluetoothGattCharacteristic.PROPERTY_WRITE,
            BluetoothGattCharacteristic.PERMISSION_READ or BluetoothGattCharacteristic.PERMISSION_WRITE
        )


        // Serializing objects
        val characteristicData = CharacteristicDataBroadcast(email.substringBefore("@"))
        val dataJsonString = Json.encodeToString(characteristicData)
        // Write data to the characteristic
        characteristic.value = dataJsonString.toByteArray(Charsets.UTF_8)

        service.addCharacteristic(characteristic)

        // Add the service to the server
        bluetoothGattServer.addService(service)

        // Start advertising the service
        val settings =
            AdvertiseSettings.Builder().setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .build()
        val data = AdvertiseData.Builder().addServiceUuid(ParcelUuid(service.uuid)).build()
        val advertiser = bluetoothAdapter.bluetoothLeAdvertiser
        advertiser.startAdvertising(settings, data, advertiseCallback)
    }

    private val advertiseCallback = object : AdvertiseCallback() {
        override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
            Log.i("Bluetooth", "Advertising started successfully")
        }

        override fun onStartFailure(errorCode: Int) {
            Log.e("Bluetooth", "Advertising failed with error code: $errorCode")
        }
    }

    @SuppressLint("MissingPermission")
    fun closeBluetoothGattServer(gattServer: BluetoothGattServer) {
        gattServer.close()
    }

    override fun onBackPressed() {
        // Do nothing
    }
    fun tryGeneratingUUIDWithPrefix(prefix: String): UUID? {
        for (i in 1..10) {
            val uuid = UUID.fromString(UUID.randomUUID().toString().replaceRange(
                0..7, CustomServiceUuidPrefix
            ))
            if (uuid.version() == 4 && (uuid.variant() and 0x80) == 0) {
                Log.d(TAG,"Generated UUID --> $uuid")
                return uuid
            }
        }
        return null
    }
    @SuppressLint("MissingPermission")
    fun removeGattServicesWithPrefix(gattServer: BluetoothGattServer, uuidPrefix: String) {
        val servicesToRemove = mutableListOf<BluetoothGattService>()
        for (service in gattServer.services) {
            val uuid = service.uuid.toString()
            if (uuid.startsWith(uuidPrefix)) {
                servicesToRemove.add(service)
            }
        }
        for (service in servicesToRemove) {
            gattServer.removeService(service)
        }
    }

}