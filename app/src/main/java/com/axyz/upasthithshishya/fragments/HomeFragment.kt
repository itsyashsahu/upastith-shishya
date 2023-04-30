package com.axyz.upasthithshishya.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.Realm.Course
import com.axyz.upasthithshishya.Realm.CourseRepository
import com.axyz.upasthithshishya.Realm.InvitationRecord
import com.axyz.upasthithshishya.activity.Profile
import com.axyz.upasthithshishya.app
import com.axyz.upasthithshishya.courses.CourseInfo
import com.axyz.upasthithshishya.courses.CourseListAdapter
import com.axyz.upasthithshishya.data.realmModule
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.mongodb.ext.call
import io.realm.kotlin.mongodb.ext.customDataAsBsonDocument
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.BsonBoolean
import org.mongodb.kbson.ObjectId

class HomeFragment : Fragment() {
    var courseList : RealmList<Course> = realmListOf()
    private lateinit var courseListAdapter: CourseListAdapter
    private lateinit var notificationMessages: MutableList<String>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.homeRecView)
        val searchView = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.search_course)
        courseListAdapter = CourseListAdapter(courseList)
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
        view.findViewById<ImageView>(R.id.appNotificaitons).setOnClickListener {
            openDialogBox(it)
        }
        view.findViewById<ImageView>(R.id.homeProfileImageView).setOnClickListener{
                val intent = Intent(this@HomeFragment.requireContext(), Profile::class.java)
                startActivity(intent)
            }

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
        return view
    }
    private fun openDialogBox(view: View) {

        val popupWindow = PopupWindow(requireContext())
        val contentView = LayoutInflater.from(requireContext()).inflate(R.layout.item_course_registration_notification_list, null)
        popupWindow.contentView = contentView
        popupWindow.isFocusable = true
        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.elevation = 10F

        val notificationListLinearLayout = contentView.findViewById<LinearLayout>(R.id.courseNotificationLinearLayout)
        val scrollView = contentView.findViewById<ScrollView>(R.id.scrollView)
        var invitations:RealmResults<InvitationRecord>
        notificationMessages = mutableListOf()
        CoroutineScope(Dispatchers.Main).launch {
            invitations= realmModule.realm.query<InvitationRecord>().find()
            invitations.forEach(){
                if (it.status == "sent")
                {
                    val message = "${it.invitedByTeacherEmail} invites you to the course ${it.courseName}?"
                    notificationMessages.add(message)
                    val notification = createNotificationView(message,it)
                    notificationListLinearLayout.addView(notification)
                }
            }
        }
        popupWindow.showAsDropDown(view)
    }

    private fun createNotificationView(notificationText: String,courseInvite: InvitationRecord): View {
        val notificationView = LayoutInflater.from(requireContext()).inflate(R.layout.item_course_notification, null)

        val courseNotificationTextView = notificationView.findViewById<TextView>(R.id.courseNotification)
        val acceptButton = notificationView.findViewById<Button>(R.id.acceptCourseInvitation)
        val rejectButton = notificationView.findViewById<Button>(R.id.rejectCourseInvitation)

        courseNotificationTextView.text = notificationText

        acceptButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val studentEmail = app.currentUser?.customDataAsBsonDocument()?.getValue("email")
                    ?.asString()?.value.toString()
                val functionResponse = app.currentUser?.functions
                    ?.call<BsonBoolean>("AcceptInvitation",courseInvite._id,studentEmail,
                        courseInvite.courseId.let { ObjectId(it) }, app.currentUser!!.id.toString())
                Log.d("user", "user usha function :: ${functionResponse}")
            }
        }

        rejectButton.setOnClickListener {
            // Handle reject button click here
        }

        return notificationView
    }




    private fun filterList(query : String?){
        if(query!=null){
            val filteredList = kotlin.collections.ArrayList<Course>()
            val formattedQuery = query.replace("\\s".toRegex(), "") // Remove all spaces from the search query
            for (course in courseList) {
                val formattedCourseName = course.name.toString().replace("\\s".toRegex(), "") // Remove all spaces from the course name
                if (formattedCourseName.lowercase().contains(formattedQuery.lowercase())) {
                    filteredList.add(course)
                }
            }
            courseListAdapter.setFilteredList(filteredList)
        }
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
                    courseListAdapter?.notifyDataSetChanged()
                } else {
                    // Do something when the Realm data is not synced yet
                    Log.d("Sync Update :: ", "------- Sync NOT-COMPLETED ------- ")
                }
            }
        }

    }
}