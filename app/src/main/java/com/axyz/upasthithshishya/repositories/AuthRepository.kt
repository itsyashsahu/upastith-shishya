package com.axyz.upasthithshishya.repositories

import com.axyz.upasthithshishya.apidata.LoginRequest
import com.axyz.upasthithshishya.apidata.LoginResponseObject
import com.axyz.upasthithshishya.apidata.ResponseObj
import com.axyz.upasthithshishya.apidata.SignupRequest
import com.axyz.upasthithshishya.other.Resource


interface AuthRepository {
    suspend fun signupUser(signupRequest: SignupRequest): Resource<ResponseObj>

    fun saveAuthToken(authToken :String)

    fun saveExtraDetails(_id:String,name:String,email:String,description: String)

    suspend fun loginUser(loginRequest: LoginRequest): Resource<LoginResponseObject>
}