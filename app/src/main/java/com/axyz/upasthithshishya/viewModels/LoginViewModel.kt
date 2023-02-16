package com.axyz.upasthithshishya.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axyz.upasthithshishya.apidata.LoginRequest
import com.axyz.upasthithshishya.apidata.LoginResponseObject
import com.axyz.upasthithshishya.apidata.ResponseObj
import com.axyz.upasthithshishya.apidata.SignupRequest
import com.axyz.upasthithshishya.other.Event
import com.axyz.upasthithshishya.other.Resource
import com.axyz.upasthithshishya.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
//    constructor() : this(authRepository = AuthRepository())

    // Mutable Value
    private val _loginStatus = MutableLiveData<Event<Resource<LoginResponseObject>>>()

    // Immutable Value
    val signupStatus: LiveData<Event<Resource<LoginResponseObject>>> = _loginStatus

    fun login(loginRequest: LoginRequest) {
        _loginStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO){
            val resp = authRepository.loginUser(loginRequest)
            _loginStatus.postValue(Event(resp))
        }
    }
}