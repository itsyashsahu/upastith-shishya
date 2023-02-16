package com.axyz.upasthithshishya.apidata

data class loggedInStudent(
    val id: String,
    val batch: String="N/A",
    val branch: String ="N/A",
    val courses: List<Any>,
    val email: String,
    val name: String,
    val rollno: String="N/A",
    val userType: String
)