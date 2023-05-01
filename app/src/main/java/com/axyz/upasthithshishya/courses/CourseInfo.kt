package com.axyz.upasthithshishya.courses

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.axyz.upasthithshishya.Realm.Course
import com.axyz.upasthithshishya.Realm.CourseRepository
import com.axyz.upasthithshishya.Realm.StudentRecord
import com.axyz.upasthithshishya.activity.ShowStudentAttendance
import com.axyz.upasthithshishya.data.realmModule.realm
import com.axyz.upasthithshishya.databinding.ActivityCourseInfoBinding
import io.realm.kotlin.ext.query
import org.mongodb.kbson.ObjectId

class CourseInfo : AppCompatActivity() {
    private lateinit var binding: ActivityCourseInfoBinding
    private lateinit var courseName:TextView
    private lateinit var courseCode:TextView
    private lateinit var department:TextView
    private lateinit var semester:TextView
    private lateinit var credits:TextView
    private lateinit var description:TextView
    private lateinit var attendanceRecordsButton:AppCompatButton
    private lateinit var studentAttendance: CalendarView
    private lateinit var courseId: ObjectId
    private lateinit var course: Course
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCourseInfoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        courseId = intent.getByteArrayExtra("Course Id")?.let { ObjectId(it) }!!
        courseName = binding.courseNameTextView
        courseCode = binding.courseCodeTextView
        department = binding.departmentTextView
        semester = binding.semesterTextView
        credits = binding.creditsTextView
        description = binding.descriptionTextView
        attendanceRecordsButton = binding.attendanceRecordsButton
        attendanceRecordsButton.setOnClickListener{
            val intent = Intent(this,ShowStudentAttendance::class.java)
            intent.putExtra("Course Id",courseId.toByteArray())
            startActivity(intent)
        }
        studentAttendance = binding.courseInfoStudentAttendanceCalendar
        course = CourseRepository().getCourse(courseId)!!
        if (course!=null)
        {
            courseName.text = course.name
            courseCode.text = course.courseCode
            department.text = "Department - " + course.courseDepartment
            description.text = course.courseDescription
            credits.text = "Credits - " + course.courseCredits
            semester.text = "Semester - " + course.courseSemester
        }
//        Log.d("Course","Course Details -------> ${course.name}")
//        val st = realm.query<StudentRecord>().find()
//        st.forEach { student ->
//            Log.d("Course","Student Details -------> ${student.studentEmailId}")
//        }
//        Log.d("Course","Student Details -------> ${st}")

    }
}