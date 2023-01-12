package com.axyz.upasthithshishya

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class StartAttendance : AppCompatActivity() {

    class DiscoveredBluetoothDevice(val device: BluetoothDevice, val serviceUuids: List<ParcelUuid>)

    val deviceSet = mutableListOf<DiscoveredBluetoothDevice>()
    val rollList = mutableListOf<String>()
    lateinit var adapter: StringAdapter

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_for_attendence)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = StringAdapter(rollList)
        recyclerView.adapter = adapter

        // Check if Bluetooth is supported on the device
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            ?: // Device does not support Bluetooth
            return

        // Check if Bluetooth is enabled
        if (!bluetoothAdapter.isEnabled) {
            // Bluetooth is not enabled, request permission to enable it
//            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            val MY_PERMISSION_REQUEST_CODE = 123

            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            startActivityForResult(enableBtIntent, MY_PERMISSION_REQUEST_CODE)
//            startActivityForResult(enableBtIntent, MY_PERMISSION_REQUEST_CODE)
        }

        scan(bluetoothAdapter)
//        if (PermissionUtils.hasPermissions(this)) {
//        } else {
//            println("BroadCasting BLocked -> ${PermissionUtils.hasPermissions(this)}")
//            PermissionUtils.requestPermissions(this)
//        }
//        rollList.add("TEst")
//        adapter.notifyDataSetChanged()

    }

    @SuppressLint("MissingPermission")
    private fun scan(bluetoothAdapter :BluetoothAdapter) {
        println("Started Attendance Taking -- ")
//        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//
//        // Check if Bluetooth is supported on the device
//        if (bluetoothAdapter == null) {
//            Log.e("Bluetooth", "Bluetooth is not supported on this device")
//            return
//        }
//
//        // Check if Bluetooth is enabled
//        if (!bluetoothAdapter.isEnabled()) {
//            Log.i("Bluetooth", "Bluetooth is not enabled, enabling now...")
//            bluetoothAdapter.enable()
//        }
//        if (bluetoothAdapter == null) {
//            // Bluetooth is not supported on this device
//            return
//        }
//
//        if (!bluetoothAdapter.isEnabled) {
//            // Bluetooth is disabled, prompt the user to enable it
//            return
//        }

        val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
        val scanSettings = ScanSettings.Builder().build()
        val scanFilter = ScanFilter.Builder()
//            .setServiceUuid(ParcelUuid.fromString("f4f5f6f9-0000-0000-0000-000000000000"))
            .build()

        // List to store the scan results
        val scanResultList: MutableList<ScanResult> = mutableListOf()

        val scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                // Add the scan result to the list
                scanResultList.add(result)

                // Get the ScanRecord object from the ScanResult
                val scanRecord: ScanRecord? = result.getScanRecord()

                val device: BluetoothDevice = result.device
                // Check if the device is already in the list
                if (deviceSet.any { it.device == device }) {
                    // Device is already in the list, do nothing
                } else {
                    // Print the service UUIDs
                    val serviceUuids: List<ParcelUuid> =
                        scanRecord?.getServiceUuids() ?: emptyList()
                    if (serviceUuids.isNotEmpty()) {
                        println("Service UUIDs: $serviceUuids")
                        for (service_uuid in serviceUuids) {
                            if (service_uuid.toString().startsWith("f4f5f6f9")) {
                                // Device is new, and has our made uuid now add it to the list
                                println("Yes this $service_uuid starts with f4f5f6f9")
//                                if (deviceSet.any { it.device == device }) {
                                deviceSet.add(
                                    DiscoveredBluetoothDevice(
                                        device,
                                        serviceUuids
                                    )
                                )
                                connectToDevice(device);
                                println("Device Added ------------> $device")
//                                }
                            }
                        }
                    } else {
                        println("No service UUIDs available")
                    }

                }
            }
        }


        println("Scanning Started Successfully ---------- ")
        bluetoothLeScanner.startScan(listOf(scanFilter), scanSettings, scanCallback)
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice) {
        println("Connect to Device -------------->>>> $device")
        device.connectGatt(this@StartAttendance, false, object : BluetoothGattCallback() {
            lateinit var studentRollNo: String
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    println("Connect to Device :: --> $device")
                    gatt.discoverServices()
                }
                if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    println("Disconneted from the device :: -> $device")
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    val services = gatt.services
                    for (service in services) {
                        if (service.uuid.toString().startsWith(CustomServiceUuidPrefix)) {
                            // make uuid from our coustomCharUuid
                            val characteristicId = UUID.fromString(CustomCharUuid)
                            // get that characteristic
                            val characteristic = service.getCharacteristic(characteristicId)

                            // send the request to read the characteristic to get the rollno.
                            if (gatt.readCharacteristic(characteristic)) {
                                // Request was sent successfully
                                println("Request was sent successfully")
                            } else {
                                // Request failed
                            }

                        }
                    }
                }
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, status)
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    // Characteristic read successfully
                    val jsonString = characteristic?.getStringValue(0)
                    try {
                        val jsonObject = jsonString?.let { JSONObject(it) }
                        // do something with the JSON object
                        if (jsonObject != null) {
                            println("Characteristic Read -> ${jsonObject.get("RollNo") as String}")

                            // get the RollNo of the student
                            val RollNo = addTimeToString(jsonObject.get("RollNo") as String)

                            // mark the attendence or store it in the record
                            storeRecord(RollNo)

//                            deviceSet.remove(device)
//                            deviceSet.remove(device)

                            val handler = Handler(Looper.getMainLooper())
                            handler.post {
                                adapter.notifyDataSetChanged()
                            }

                            // send request to writecharacteristic
                            // make data to be written
                            val characteristicData = CharacteristicDataClass(true)
                            val dataJsonString = Json.encodeToString(characteristicData)
                            val value = dataJsonString.toByteArray(Charsets.UTF_8)
                            // write the new data
                            characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT)
                            characteristic.setValue(value)
                            if (gatt != null) {
                                if (gatt.writeCharacteristic(characteristic)) {
                                    // Request was sent successfully
                                    println("Write Request ------------> $value")
                                } else {
                                    println("Write Request ------------> FAILED")
                                    // Request failed
                                }
                            }
                        }

                    } catch (e: JSONException) {
                        Log.e("JSON", "Error decoding JSON string", e)
                    }
                } else {
                    // Characteristic read failed
                    println("Characteristic read failed")
                }
            }

            override fun onCharacteristicWrite(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicWrite(gatt, characteristic, status)
                println("Characteristic Wrote Successfully -> ::  $device")

//                rollList.add(studentRollNo)
                if (gatt != null) {
                    gatt.disconnect()
                }
            }

        })

    }

    fun storeRecord(studentRollNo: String) {
        rollList.add(studentRollNo)
    }

    class StringAdapter(private val strings: List<String>) :
        RecyclerView.Adapter<StringAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.text_view)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = strings[position]
        }

        override fun getItemCount(): Int {
            return strings.size
        }
    }

    fun addTimeToString(s: String): String {
        val currentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm:ss")
        val time = formatter.format(currentTime)
        return "$s - $time"
    }

}