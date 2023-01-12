package com.axyz.upasthithshishya.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
//    constructor() : this(authRepository = AuthRepository())

    // Mutable Value
    private val _signupStatus = MutableLiveData<Event<Resource<ResponseObj>>>()

    // Immutable Value
    val signupStatus: LiveData<Event<Resource<ResponseObj>>> = _signupStatus

    fun signup(signupRequest:SignupRequest) {
        _signupStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO){
            val resp = authRepository.signupUser(signupRequest)
            _signupStatus.postValue(Event(resp))
        }
    }
}