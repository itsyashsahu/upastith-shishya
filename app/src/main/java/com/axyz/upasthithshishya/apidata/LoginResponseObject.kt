package com.axyz.upasthithshishya.apidata

data class LoginResponseObject(
    val authToken: String,
    val loggedInStudent: loggedInStudent,
    val success: Boolean
)