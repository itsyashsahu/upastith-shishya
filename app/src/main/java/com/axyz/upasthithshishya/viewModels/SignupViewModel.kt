package com.axyz.upasthithshishya.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axyz.upasthithshishya.apidata.ResponseObj
import com.axyz.upasthithshishya.apidata.SignupRequest
import com.axyz.upasthithshishya.data.AuthRepository
import com.axyz.upasthithshishya.other.Event
import com.axyz.upasthithshishya.other.Resource
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

    fun signup(email:String, password:String) {
        _signupStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO){
            try{
                val res = authRepository.createAccount(email, password)
                Log.d("Create Account :: ","Result -- $res")
                authRepository.login(email, password)
                _signupStatus.postValue(Event(res))
            }catch (e:Exception) {
                Log.d("Create Account :: ", "Exception msg-- ${e.message}")
                if(e.message?.contains("name already in use") == true) {
                    val res = authRepository.login(email, password)
                    if(res.toString().contains("Success")) {
                        _signupStatus.postValue(Event(Resource.Error("Email already in use Loggin you In")))
                    }else{
                        _signupStatus.postValue(Event(Resource.Error("Email already in use Loggin Caused an Error")))
                    }
                }else{
                    Log.d("Create Account :: ", "Something went wrong")
                    _signupStatus.postValue(Event(Resource.Error("Something went wrong")))
                }
            }
        }
    }
}