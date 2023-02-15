package com.axyz.upasthithshishya.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.bluetooth.*
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
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
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

@Serializable
data class CharacteristicDataClass(val attendance: Boolean)

@Serializable
data class CharacteristicDataBroadcast(val RollNo: String?)


val CoustomCharUuid = "ff82240a-27c1-4661-a15a-be5a22a17256"
val CoustomServiceUuidPrefix = "f4f5f6f9"

class MarkAttendence : AppCompatActivity() {

    private lateinit var bluetoothGattServer: BluetoothGattServer
    // Data Class for Characteristics Object That is written & Read
    lateinit var RollNo: String
    //    = intent.getStringExtra("Roll_no")
    lateinit var uservice: String

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a Dialog object and set its content view.
        val dialog = Dialog(this)
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
                startActivity(Intent(this,GiveAttendance::class.java))
            }
        }, 30000)

        // Show the dialog.
        dialog.show()
        RollNo = intent.getStringExtra("RollNo").toString()

        // println("The Value of Roll-No -> $RollNo")
        startGattSever()
        // if (PermissionUtils.hasPermissions(this)) {
        // } else {
            // println("BroadCasting BLocked --------------------------------------------> ${!PermissionUtils.hasPermissions(this)}")
            // PermissionUtils.requestPermissions(this)
        // }
    }

    @SuppressLint("MissingPermission")
    private fun startGattSever() {

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        // Check if Bluetooth is supported on the device
        if (bluetoothAdapter == null) {
            Log.e("Bluetooth", "Bluetooth is not supported on this device")
            return
        }

        // Check if Bluetooth is enabled
        if (!bluetoothAdapter.isEnabled) {
            Log.i("Bluetooth", "Bluetooth is not enabled, enabling now...")
            bluetoothAdapter.enable()

        }

        if (!bluetoothAdapter.isEnabled) {
            Log.i("Bluetooth UPDate", "Bluetooth is not enabled")
//            bluetoothAdapter.enable()
        } else {
            Log.i("Bluetooth UPDate", "Bluetooth is enabled")
        }


//             Get the BluetoothLeAdvertiser
        val bluetoothLeAdvertiser = bluetoothAdapter.bluetoothLeAdvertiser

        // Check if Bluetooth LE advertising is supported on the device
        if (bluetoothLeAdvertiser == null) {
            Log.e("Bluetooth", "Bluetooth LE advertising is not supported on this device")
            return
        }

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager


        bluetoothGattServer =
            bluetoothManager.openGattServer(this, object : BluetoothGattServerCallback() {

                override fun onConnectionStateChange(
                    device: BluetoothDevice, status: Int, newState: Int
                ) {
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        println("Connected to the device - ${device.name ?: device.address}")
                    }
                }

                override fun onCharacteristicReadRequest(
                    device: BluetoothDevice,
                    requestId: Int,
                    offset: Int,
                    characteristic: BluetoothGattCharacteristic
                ) {

                    //                the below code shows the broadcasting device a toast that some device has
                    //                requested to read the data and we are allowing them to read
                    //                val handler = Handler(Looper.getMainLooper())
                    //                handler.post {
                    //                    Toast.makeText(this@MainActivity, "Requested to Read Characteristics", Toast.LENGTH_SHORT).show()
                    //                }
                    //                Toast.makeText(this@MainActivity,"Requested to Read Characteristics",Toast.LENGTH_SHORT).show()
                    //                characteristic.value = "BroadCasted Guys !!!".toByteArray(Charsets.UTF_8)
                    println("request recieved from the device $device")
                    // Send the read response
                    bluetoothGattServer.sendResponse(
                        device, requestId, BluetoothGatt.GATT_SUCCESS, 0, characteristic.value
                    )
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
                    // Your code here
                    // Serializing objects
                    //                val characteristicData = CharacteristicDataClass(true)
                    //                val dataJsonString = Json.encodeToString(characteristicData)
                    //                characteristic.value=dataJsonString.toByteArray(Charsets.UTF_8)

                    val obj = Json.decodeFromString<CharacteristicDataClass>(
                        String(
                            value, Charsets.UTF_8
                        )
                    )
                    // add a coutom check to identify that the writer is our desired teacher only
                    // just for demo purpose
                    if (obj.attendance == true) {
                        characteristic.value = value

                        val service: BluetoothGattService =
                            bluetoothGattServer.getService(characteristic.service.uuid)
                        bluetoothGattServer.removeService(service)

                        val handler = Handler(Looper.getMainLooper())
                        handler.post {
                            Toast.makeText(
                                this@MarkAttendence,
                                //                            "Characteristics OverWritten with -> $value",
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
                        finish()
                    }
                }
            })

        uservice = UUID.randomUUID().toString().replaceRange(
            0..7, CoustomServiceUuidPrefix
        )

        // Add the service to the server
        val service = BluetoothGattService(
            UUID.fromString(uservice), BluetoothGattService.SERVICE_TYPE_PRIMARY
        )
        println("Characterist -> CoustomCharUuid  &&  ")
        val characteristic = BluetoothGattCharacteristic(
            UUID.fromString(CoustomCharUuid),
            BluetoothGattCharacteristic.PROPERTY_READ or BluetoothGattCharacteristic.PROPERTY_WRITE,
            BluetoothGattCharacteristic.PERMISSION_READ or BluetoothGattCharacteristic.PERMISSION_WRITE
        )


        // Serializing objects
        val characteristicData = CharacteristicDataBroadcast(RollNo)
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
}