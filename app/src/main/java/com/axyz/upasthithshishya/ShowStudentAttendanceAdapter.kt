package com.axyz.upasthithshishya

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithshishya.Realm.StudentRecord
import io.realm.kotlin.query.RealmResults
import java.text.SimpleDateFormat
import java.util.*

class ShowStudentAttendanceAdapter(private val classAttendance: RealmResults<StudentRecord>) :
    RecyclerView.Adapter<ShowStudentAttendanceAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.ClassNumberView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_show_student_attendance, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateString = classAttendance[position].timeOfAttendance
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
        val date = dateFormat.parse(dateString)
        val dateStr = SimpleDateFormat("yyyy-MM-dd").format(date)
        val classNumber = classAttendance[position].classNumber
        holder.textView.text = "Class Number $classNumber ($dateStr)"
    }

    override fun getItemCount(): Int {
        return classAttendance.size
    }
}