package com.axyz.upasthithshishya.repositories
//
//
//import android.content.SharedPreferences
//import android.util.Log
//import com.axyz.upasthithshishya.Realm.Course
////import com.axyz.upasthithshishya.Realm.CourseManager
////import com.axyz.upasthithshishya.Realm.Student
////import com.axyz.upasthithshishya.Realm.StudentManager
//import com.axyz.upasthithshishya.api.APIInterface
//import com.axyz.upasthithshishya.apidata.*
//import com.axyz.upasthithshishya.other.Resource
//import com.google.gson.Gson
//import retrofit2.HttpException
//import java.io.IOException
//import javax.inject.Inject
//
//class DefaultAuthRepository @Inject constructor(
//    private val retrofitApi: APIInterface,
//    private val sharedPreferences: SharedPreferences,
//    private val gson: Gson
//) : AuthRepository {
//
//    override suspend fun loginUser(loginRequest: LoginRequest): Resource<LoginResponseObject> {
//        val response = try {
//            retrofitApi.login(loginRequest)
//        } catch (e: IOException) {
//            Log.e("LoginActivity", "IOEException, no internet?", e)
//            return Resource.Error("Network Failure")
//        } catch (e: HttpException) {
//            Log.e("LoginActivity", "HTTP exception, unexpected response", e)
//            return Resource.Error("Network Failure")
//        }
//        Log.d("LoginActivity", "Response: $response")
//        if (response.isSuccessful && response.body() != null) {
//            Log.d("LoginActivity", "Login Successful ${response.body()}")
//
//
//            // Save the Student to Local Realm Database
//            val fetchedStudentFromLoginRequest = response.body()!!.loggedInStudent
//            Log.d("LoginActivity", "Login Successful ${response.body()}")
//
//            val email = fetchedStudentFromLoginRequest.email
//            // Check if the email ends with "@iiitdmj.ac.in"
//            var batch = "N/A"
//            var branch = "N/A"
//            var rollno = "N/A"
//            if (email.endsWith("@iiitdmj.ac.in") && email.length == 22) {
//                // Extract the first 2 letters and the next 3 letters
//                batch = "20" + email.substring(0, 2)
//                branch = email.substring(2, 5).uppercase()
//                rollno = email.substring(0, 7).uppercase()
//                // Print the values of batch and branch
//                println("Batch: $batch")
//                println("Branch: $branch")
//                println("Rollno: $rollno")
//
//            } else {
//                // Email is not in the expected format
//                println("Invalid email format")
//            }
//
//
//            // TODO Fetch and sync courses from server
////            val createdStudentFromResponse = Student(
////                fetchedStudentFromLoginRequest.id,
////                fetchedStudentFromLoginRequest.email,
////                fetchedStudentFromLoginRequest.name,
////                batch,
////                branch,
////                rollno,
////                fetchedStudentFromLoginRequest.courses as MutableList<String>
////            )
//
////            StudentManager().insertStudent(createdStudentFromResponse);
//
//            saveExtraDetails(
//                fetchedStudentFromLoginRequest.id,
//                fetchedStudentFromLoginRequest.name,
//                fetchedStudentFromLoginRequest.email,
//                "Dummy Description"
//            )
////            Log.d(
////                "LocalData Student :: ",
//////                "${StudentManager().getStudent(fetchedStudentFromLoginRequest.id)}"
////            )
//            saveAuthToken(response.body()!!.authToken)
////            CourseManager().insertCourse(Course("123","SDN","CS8011","Learn how filters image manipulation works! using Python & OpenCV. Build Awesome Projects","3","All","2023","6","lkjhlkjh"))
//            return Resource.Success(response.body()!!)
//        } else {
//            Log.d("LoginActivity", "Login Failed")
//            response.errorBody()?.let {
//                Log.e("LoginActivity", it.string())
//            }
//        }
//        return Resource.Error("Login Failed")
//    }
//
//    override suspend fun signupUser(signupRequest: SignupRequest): Resource<ResponseObj> {
//        val response = try {
//            retrofitApi.signup(signupRequest)
//        } catch (e: IOException) {
//            Log.e("LoginActivity", "IOEException, no internet?", e)
//            return Resource.Error("Network Failure")
//        } catch (e: HttpException) {
//            Log.e("LoginActivity", "HTTP exception, unexpected response", e)
//            return Resource.Error("Network Failure")
//        }
//        Log.d("LoginActivity", "Response: $response")
//        if (response.isSuccessful && response.body() != null) {
//            Log.d("LoginActivity", "Login Successful ${response.body()}")
//            saveAuthToken(response.body()!!.authToken)
//            saveExtraDetails(
//                "NULL", signupRequest.name, signupRequest.email, signupRequest.description
//            )
//            return Resource.Success(response.body()!!)
//        } else {
//            Log.d("LoginActivity", "Login Failed")
//            response.errorBody()?.let {
//                Log.e("LoginActivity", it.string())
//            }
//        }
//        return Resource.Error("Login Failed")
//    }
//
////    override suspend fun getStudentDetailHandler(  ):Resource<ResponseObj>{
////
////    }
//
//    override fun saveAuthToken(authToken: String) {
//        sharedPreferences.edit().putString("authToken", authToken).apply()
//    }
//
//    override fun saveExtraDetails(_id: String, name: String, email: String, description: String) {
//        sharedPreferences.edit().putString("_id", _id).apply()
//        sharedPreferences.edit().putString("name", name).apply()
//        sharedPreferences.edit().putString("email", email).apply()
//
//
//        sharedPreferences.edit().putString("description", description).apply()
//    }
//
//}