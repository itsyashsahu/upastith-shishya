package com.axyz.upasthithshishya.courses.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.axyz.upasthithshishya.activity.GiveAttendance
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.activity.ProfileActivity

class AttendanceFragment : Fragment() {
    private lateinit var courseDropDownButton: AutoCompleteTextView
    private val courseNames = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        courseDropDownButton = view.findViewById(R.id.courseDropDownView)
        updateDropDownAdapter()
    }

    override fun onResume() {
        super.onResume()
        updateDropDownAdapter()
    }

    private fun updateDropDownAdapter() {
        courseNames.clear()
//        courseNames.addAll(courseManager.getAllCourse().map { it.name })
        courseDropDownButton.setAdapter(ArrayAdapter(requireContext(), R.layout.course_list_dropdown, courseNames))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_attendance, container, false)

        view.findViewById<ImageView>(R.id.attendanceFragmentProfile).setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }

        view.findViewById<Button>(R.id.giveAttendanceButton).setOnClickListener {
            val selectedCourse = courseDropDownButton.text.toString().trim()
            if (selectedCourse.isNotEmpty() && courseNames.contains(selectedCourse)) {
                startActivity(Intent(requireContext(), GiveAttendance::class.java))
            } else {
                Toast.makeText(requireContext(), "Please select a course name from the dropdown", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}

