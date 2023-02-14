package com.axyz.upasthithshishya.courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.Realm.Course

class CourseListAdapter (var courseList: List<Course>):RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_courses, parent, false)
        return CourseListViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        val currentItem = courseList[position]
        holder.courseName.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return courseList.size
    }
    inner class CourseListViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val courseName:TextView = itemView.findViewById(R.id.courseNameRecView)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}