package com.axyz.upasthithshis.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.Realm.CourseManager
import com.axyz.upasthithshishya.courses.CourseInfo
import com.axyz.upasthithshishya.courses.CourseListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {
//    var courseManager = CourseManager()
//    var courseList = courseManager.getAllCourse()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.homeRecView)
//        val courseListAdapter = CourseListAdapter(courseList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
//        recyclerView.adapter = courseListAdapter
//        Log.d("Course","Course list -> $courseList")
//        courseManager.insertCourse(Course("128","Software Defined Networking","CS2008","SDN Course","3","CSE","2020","5","lkjhlkjh"))
//        courseListAdapter.setOnItemClickListener(object : CourseListAdapter.onItemClickListener {
//            override fun onItemClick(position: Int) {
//                val intent = Intent(this@HomeFragment.requireContext(), CourseInfo::class.java)
//                intent.putExtra("Course Name", courseList[position].name)
//                intent.putExtra("Course Code", "COURSE CODE " + courseList[position].courseCode)
//                intent.putExtra("Department",courseList[position].courseDepartment)
//                intent.putExtra("Semester",courseList[position].courseSemester)
//                startActivity(intent)
//            }
//        })
        view.findViewById<ImageView>(R.id.loginImageView).setOnClickListener{
//            val intent = Intent(this@Home.requireContext(), Profile::class.java)
//            startActivity(intent)
        }
        view.findViewById<FloatingActionButton>(R.id.addNewCourseFAB).setOnClickListener {
//            val intent = Intent(this@Home.requireContext(), AddNewCourse::class.java)
//            startActivity(intent)
        }
        return view
    }
}