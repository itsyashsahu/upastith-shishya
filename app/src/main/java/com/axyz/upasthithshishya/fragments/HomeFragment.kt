package com.axyz.upasthithshishya.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.Realm.Course
import com.axyz.upasthithshishya.Realm.CourseRepository
import com.axyz.upasthithshishya.activity.Profile
import com.axyz.upasthithshishya.courses.CourseInfo
import com.axyz.upasthithshishya.courses.CourseListAdapter
import com.axyz.upasthithshishya.data.realmModule
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
//    var courseManager = CourseManager()
//    var courseList = courseManager.getAllCourse()
var courseList : RealmList<Course> = realmListOf<Course>()
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
    val courseListAdapter = CourseListAdapter(courseList)
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.adapter = courseListAdapter

    courseListAdapter.setOnItemClickListener(object : CourseListAdapter.onItemClickListener {
        override fun onItemClick(position: Int) {
            val id = courseList[position]._id.toByteArray()
            val intent = Intent(this@HomeFragment.requireContext(), CourseInfo::class.java)
            intent.putExtra("Course Id",id)
            startActivity(intent)
        }
    })

//    view.findViewById<ImageView>(R.id.loginImageView).setOnClickListener{
//            val intent = Intent(this@HomeFragment.requireContext(), Profile::class.java)
//            startActivity(intent)
//        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            realmModule.isSynced.observe(viewLifecycleOwner) { isSynced ->
                if (isSynced) {
                    Log.d("Sync Update :: ", "------- Sync COMPLETED ------- ")
                    val fetchedCourses = CourseRepository().getAllCourse()
                    for (course in fetchedCourses) {
                        var alreadyExists = false
                        for (existingCourse in courseList) {
                            if (course._id == existingCourse._id) {
                                alreadyExists = true
                                break
                            }
                        }
                        if (!alreadyExists) {
                            courseList.add(course)
                        }
                    }
                    Log.d("Course ::: ", "Course list hai bhai ----> $courseList")
                    Log.d(
                        "Course ::: ",
                        "Course hai course hai ----> ${CourseRepository().getAllCourse()}"
                    )
//                 Do something when the Realm data is synced
                } else {
                    // Do something when the Realm data is not synced yet
                    Log.d("Sync Update :: ", "------- Sync NOT-COMPLETED ------- ")
                }
            }
        }

    }
}