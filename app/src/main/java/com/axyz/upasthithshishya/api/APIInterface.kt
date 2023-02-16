package com.axyz.upasthithshishya.api

import android.content.Context
import com.axyz.upasthithshishya.apidata.LoginRequest
import com.axyz.upasthithshishya.apidata.LoginResponseObject
import com.axyz.upasthithshishya.apidata.ResponseObj
import com.axyz.upasthithshishya.apidata.SignupRequest
import retrofit2.Response
import retrofit2.http.*

interface APIInterface {
    @POST("student/createStudent")
    suspend fun signup(@Body signupRequest: SignupRequest): Response<ResponseObj>

    @POST("student/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponseObject>
//
//    @GET("student/getStudentDetails/{email}")
//    suspend fun getStudentDetails(@Path("email") email: String,@Header("authToken") authToken :String) : Response<ResponseObj>

//    @POST("/session/attend")
//    suspend fun postAttendance(
//        @Header("Cookie") token: String,
//        @Body sessionDetails: QRData
//    ): Response<Boolean>

}
