package com.axyz.upasthithshishya.Realm

import android.util.Log
import com.axyz.upasthithshishya.app
import com.axyz.upasthithshishya.data.realmModule
import com.axyz.upasthithshishya.data.realmModule.realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import javax.inject.Singleton

class Course() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var name: String = ""
    var courseCode: String = ""
    var courseDescription: String = ""
    var courseCredits: String = ""
    var courseDepartment: String = ""
    var courseYear: String = ""
    var courseSemester: String = ""
    var createdByInstructor: String = ""
    //    var courseAttendances: RealmList<ClassAttendance> = realmListOf()
    var enrolledStudents: RealmList<ObjectId> = realmListOf()
    var enrolledStudent: RealmList<String> = realmListOf()
//    var enrolledStudents: RealmList<EnrolledStudent> = realmListOf()
//    val userCourses: RealmResults<UserRole> by backlinks (UserRole::courses)
    var totalNoOfClasses: Int = 0

    constructor(name:String,courseCode:String) : this() {
        this.name = name
        this.courseCode = courseCode
        this.createdByInstructor = app.currentUser!!.id
        this.totalNoOfClasses=0
    }
}



//fun RealmSyncRepository.insertCourse(course: Course) {
//    try {
////            realm.subscriptions.waitForSynchronization()
//        realm?.writeBlocking {
//            this.copyToRealm(course)
//        }
//    } catch (e: Exception) {
//        Log.d("Database Error :: ", "Inserting Course Failed - $e")
//    }
//}

//fun RealmSyncRepository.getAllCourses(): RealmQuery<Course> {
//    return realm.query<Course>()
//}

@Singleton
class CourseRepository (){
//    @Inject
//    lateinit var realmSyncRepository: RealmSyncRepository

//    val realm = realmModule.getDefaultInstance()

//    var realmSyncRepository = RealmSyncRepository.getInstance(onSyncError)
//    private val realmSyncRepository = RealmSyncRepository { session, error ->
//        // onSyncError implementation here
//    }


    //        fun getAllCourse(): Course {
    fun getAllCourse(): RealmResults<Course> {
//        realmModule.getStatus();
        val realm = realmModule.realm
//        return Course();
        return realm.query<Course>().find()
//        val realm = realmSyncRepository.realm
//        realm.subscriptions.waitForSynchronization()
//          if(realmSyncRepository.isInitialized) {
//              Log.d("Sync", "Sync Status :: ${realm.subscriptions.state}")
//          }
//        try {
//            Log.d("Database :: ", "Getting All Courses--------- ${realm.query<Course>().find()}")
//        }catch (e : Exception){
//            Log.d("Database :: ", "Getting All Courses--------- ${e}")
//        }
    }

    //    init{
//        HiltInjector.inject(this)
//    }
    fun examTry() {
//        realm.writeBlocking {
//            val course = copyToRealm(Course())
//            course.apply {
//                name = "Software Defined Networking"
//                // Embed the address in the contact object
//                var classRec = ClassAttendance().apply {
//                    date = Date(System.currentTimeMillis())
//                    ex = "hello"
//                }
//            }
//        }
    }

    fun insertCourse(course: Course) {
        try {
            val realm = realmModule.realm
//            realm.subscriptions.waitForSynchronization()
            realm.writeBlocking {
                this.copyToRealm(course)
            }
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Inserting Course Failed - $e")
        }
    }

//    fun updateCourse(
//        courseId: String,
//        name: String? = null,
//        courseDescription: String? = null,
//        courseCredits: String? = null,
//        courseDepartment: String? = null,
//        courseYear: String? = null,
//        courseSemester: String? = null,
//        serviceIdForInstructor: String? = null,
//    ) {
//        try {
//            realm.writeBlocking {
//                val course: Course? = this.query<Course>("id == $0", courseId).first().find()
//                if (course == null) {
//                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
//                } else {
//                    if (name != null) {
//                        course.name = name
//                    }
//                    if (courseDescription != null) {
//                        course.courseDescription = courseDescription
//                    }
//                    if (courseCredits != null) {
//                        course.courseCredits = courseCredits
//                    }
//                    if (courseDepartment != null) {
//                        course.courseDepartment = courseDepartment
//                    }
//                    if (courseYear != null) {
//                        course.courseYear = courseYear
//                    }
//                    if (courseSemester != null) {
//                        course.courseSemester = courseSemester
//                    }
//                    if (serviceIdForInstructor != null) {
//                        course.serviceIdForInstructor = serviceIdForInstructor
//                    }
//                    Log.d("Database Update :: ", "Updated Course Successfully")
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Course Failed - $e")
//        }
//    }

    // TODO
    // need to change this function to accept the Instructor Id and
    // then add that instructor to the course instead of asking for
    // the created instructor  of MAY BE NOT
//    fun addInstructor(
//        courseId: String,
//        instructor: Instructor
//    ) {
//        try {
//            realm.writeBlocking {
//                val course: Course? = this.query<Course>("id == $0", courseId).first().find()
//                if (course == null) {
//                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
//                } else {
//                    //TODO add Check if Student Already exits
//                    course.instructors.add(instructor)
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Course Failed - $e")
//        }
//    }
//
//    fun removeInstructor(
//        courseId: String,
//        instructor: Instructor
//    ) {
//        try {
//            realm.writeBlocking {
//                val course: Course? = this.query<Course>("id == $0", courseId).first().find()
//                if (course == null) {
//                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
//                } else {
//                    course.instructors.remove(instructor)
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Course Failed - $e")
//        }
//    }
//
//    fun sendInviteToStudent(
//        courseId: String,
//        enrolledStudent: EnrolledStudent
//    ) {
//        try {
//            realm.writeBlocking {
//                val course: Course? = this.query<Course>("id == $0", courseId).first().find()
//                if (course == null) {
//                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
//                } else {
//                    //TODO add Check if Student Already exits
//                    course.enrolledStudentsData.add(enrolledStudent)
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Course Failed - $e")
//        }
//    }
//
//    fun removeStudent(
//        courseId: String,
//        enrolledStudent: EnrolledStudent
//    ) {
//        try {
//            realm.writeBlocking {
//                val course: Course? = this.query<Course>("id == $0", courseId).first().find()
//                if (course == null) {
//                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
//                } else {
//                    course.enrolledStudentsData.remove(enrolledStudent)
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Course Failed - $e")
//        }
//    }

//    fun addAttendance(courseId: String, attendance: ClassAttendance) {
//        try {
//            realm.writeBlocking {
//                val course: Course? = this.query<Course>("id == $0", courseId).first().find()
//                if (course == null) {
//                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
//                } else {
//                    //TODO add Check if Student Already exits
//                    Log.d("DatabaseUpdate :: ", "Attendane added to the course successfully ")
//                    course.courseAttendance.add(attendance)
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Course Failed - $e")
//        }
//    }

//    fun getAttendance(courseId: String, date: Date): ClassAttendance? {
//        return try {
//            realm.query<Course>("id == $0", courseId).first()
//                .find()?.courseAttendance?.find { it -> it.date == date }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Course Failed - $e")
//            null
//        }
//    }

//    fun getAllAttendance(courseId: String): MutableList<ClassAttendance>? {
//        return try {
//            realm.query<Course>("id == $0", courseId).first().find()?.courseAttendance
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Updating Course Failed - $e")
//            null
//        }
//    }
//
//    fun deleteCourse(id: String) {
//        try {
//            realm.writeBlocking {
//                val course: Course? =
//                    this.query<Course>("id == $0", id).first().find()
//                if (course != null) {
//                    delete(course)
//                } else {
//                    Log.d("Database Error :: ", "Deleting Course Failed - Course NOT FOUND")
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("Database Error :: ", "Deleting Student Failed - $e")
//        }
//    }
//
    fun getCourse(id: ObjectId): Course? {
        return try {
            realm.query<Course>("_id == $0", id).first().find()
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Failed to Get Course - $e")
            null
        }
    }
//
}
