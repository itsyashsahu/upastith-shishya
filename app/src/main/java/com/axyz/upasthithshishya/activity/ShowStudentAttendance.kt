package com.axyz.upasthithshishya.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.Realm.ClassAttendanceManager
import com.axyz.upasthithshishya.Realm.StudentRecord
import com.axyz.upasthithshishya.ShowStudentAttendanceAdapter
import io.realm.kotlin.query.RealmResults
import org.mongodb.kbson.ObjectId

class ShowStudentAttendance : AppCompatActivity() {
    private lateinit var attendanceRV:RecyclerView
    private lateinit var attendanceRecords: RealmResults<StudentRecord>
    private lateinit var courseId: ObjectId
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_student_attendance)
        courseId = intent.getByteArrayExtra("Course Id")?.let { ObjectId(it) }!!
        attendanceRecords = ClassAttendanceManager().getClassAttendanceRecord(courseId.toHexString())
        attendanceRV = findViewById(R.id.attendanceRecordsRV)
        attendanceRV.layoutManager = LinearLayoutManager(this)
        attendanceRV.adapter = ShowStudentAttendanceAdapter(attendanceRecords)
    }
}