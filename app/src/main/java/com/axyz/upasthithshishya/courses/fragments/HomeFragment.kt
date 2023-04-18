package com.axyz.upasthithshishya.courses.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.Realm.Course
import com.axyz.upasthithshishya.activity.ProfileActivity
import com.axyz.upasthithshishya.courses.CourseInfo
import com.axyz.upasthithshishya.courses.CourseListAdapter

class HomeFragment : Fragment() {
//    var courseManager = CourseManager()
//    var courseList = courseManager.getAllCourse()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.homeRecView)
//        val courseListAdapter = CourseListAdapter(courseList)
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//        recyclerView.adapter = courseListAdapter
//        Log.d("Course","Course list -> $courseList")
//        courseManager.insertCourse(Course("123","Image Processing","CS8011","Learn how filters image manipulation works! using Python & OpenCV. Build Awesome Projects","3","All","2023","6","lkjhlkjh"))
//        courseListAdapter.setOnItemClickListener(object : CourseListAdapter.onItemClickListener {
//            override fun onItemClick(position: Int) {
//                val intent = Intent(this@HomeFragment.requireContext(), CourseInfo::class.java)
//                intent.putExtra("Course Name", courseList[position].name)
//                intent.putExtra("Course Code", "COURSE CODE " + courseList[position].courseCode)
//                intent.putExtra("Department",courseList[position].courseDepartment)
//                intent.putExtra("Semester",courseList[position].courseSemester)
//                intent.putExtra("Description",courseList[position].courseDescription)
//                startActivity(intent)
//            }
//        })
        view.findViewById<ImageView>(R.id.settingProfileImageView).setOnClickListener{
            val intent = Intent(this@HomeFragment.requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}