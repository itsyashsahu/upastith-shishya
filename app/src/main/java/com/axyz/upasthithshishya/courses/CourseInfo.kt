package com.axyz.upasthithshishya.courses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.activity.ShowAttendanceActivity

class CourseInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_info)
        val courseName = findViewById<TextView>(R.id.courseNameTextView)
        val courseHeading = findViewById<TextView>(R.id.courseHeadingTextView)
        val courseCode = findViewById<TextView>(R.id.courseCodeTextView)
        val department = findViewById<TextView>(R.id.departmentTextView)
        val semester = findViewById<TextView>(R.id.semesterTextView)
        val attendance = findViewById<LinearLayout>(R.id.attendanceView)
        val name: String? = intent.getStringExtra("Course Name")
        val code: String? = intent.getStringExtra("Course Code")
        val departmentName: String? = intent.getStringExtra("Department")
        val semesterNo: String? = intent.getStringExtra("Semester")
        courseName.text = name
        courseHeading.text = name
        courseCode.text = code
        department.text = departmentName
        semester.text = semesterNo
        attendance.setOnClickListener{
            startActivity(Intent(this, ShowAttendanceActivity::class.java))
        }
    }
}