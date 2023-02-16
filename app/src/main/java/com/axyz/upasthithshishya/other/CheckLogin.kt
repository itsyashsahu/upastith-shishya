package com.axyz.upasthithshishya.other

import android.content.Context

fun CheckLogin(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("upasthithShishya", Context.MODE_PRIVATE)
    val authToken = sharedPreferences.getString("authToken", "")
    println("authToken :: $authToken")
    return !authToken.isNullOrEmpty()
}
