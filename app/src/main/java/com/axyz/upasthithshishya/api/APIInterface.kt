package com.axyz.upasthithshishya.api

import com.axyz.upasthithshishya.apidata.LoginRequest
import com.axyz.upasthithshishya.apidata.ResponseObj
import com.axyz.upasthithshishya.apidata.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface {
    @POST("student/createStudent")
    suspend fun signup(@Body signupRequest: SignupRequest): Response<ResponseObj>

    @POST("student/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ResponseObj>

//    @POST("/session/attend")
//    suspend fun postAttendance(
//        @Header("Cookie") token: String,
//        @Body sessionDetails: QRData
//    ): Response<Boolean>

}