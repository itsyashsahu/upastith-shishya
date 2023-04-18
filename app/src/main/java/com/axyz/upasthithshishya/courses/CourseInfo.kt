package com.axyz.upasthithshishya.courses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.axyz.upasthithshishya.R

class CourseInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_info)
        val courseName = findViewById<TextView>(R.id.courseNameTextView)
//        val courseHeading = findViewById<TextView>(R.id.courseHeadingTextView)
        val courseCode = findViewById<TextView>(R.id.courseCodeTextView)
        val department = findViewById<TextView>(R.id.departmentTextView)
        val semester = findViewById<TextView>(R.id.semesterTextView)
        val credits = findViewById<TextView>(R.id.creditsTextView)
//        val attendance = findViewById<LinearLayout>(R.id.attendanceView)
        val description = findViewById<TextView>(R.id.descriptionTextView)
        val enrolledStudents = findViewById<CardView>(R.id.enrolled_students)


        val name: String? = intent.getStringExtra("Course Name")
        val code: String? = intent.getStringExtra("Course Code")
        val departmentName: String? = intent.getStringExtra("Department")
        val semesterNo: String? = intent.getStringExtra("Semester")
        val credit: String? = intent.getStringExtra("Credit")
        val courseDescription: String? = intent.getStringExtra("Description")
        courseName.text = name
//        courseHeading.text = name
        courseCode.text = code
        department.text = departmentName
        semester.text = semesterNo
        credits.text = credit
        description.text = courseDescription
//        attendance.setOnClickListener{
//            startActivity(Intent(this, ShowAttendanceActivity::class.java))
//        }
    }
}