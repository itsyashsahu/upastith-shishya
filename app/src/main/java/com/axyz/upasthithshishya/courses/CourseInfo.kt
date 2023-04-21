package com.axyz.upasthithshishya.courses

import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.Realm.Course
import com.axyz.upasthithshishya.Realm.CourseRepository
import com.axyz.upasthithshishya.Realm.StudentRecord
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
    private lateinit var enrolledStudents:CardView
    private lateinit var studentAttendance: CalendarView
    private lateinit var courseId: ObjectId
    private lateinit var course: Course
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCourseInfoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        courseId = intent.getByteArrayExtra("Course Id")?.let { ObjectId(it) }!!
        courseName = binding.courseNameTextView
        courseCode = binding.courseCodeTextView
        department = binding.departmentTextView
        semester = binding.semesterTextView
        credits = binding.creditsTextView
        description = binding.descriptionTextView
        enrolledStudents = binding.enrolledStudents
        studentAttendance = binding.courseInfoStudentAttendanceCalendar
//        course = CourseRepository().getCourse(courseId)!!
//        Log.d("Course","Course Details -------> ${course.name}")
        val st = realm.query<StudentRecord>().find()
        st.forEach { student ->
            Log.d("Course","Student Details -------> ${student.studentEmailId}")
        }
        Log.d("Course","Student Details -------> ${st}")

    }
}