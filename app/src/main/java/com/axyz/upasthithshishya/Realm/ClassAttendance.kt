package com.axyz.upasthithshishya.Realm

import java.util.Date

import android.util.Log
import com.axyz.upasthithshishya.app
import com.axyz.upasthithshishya.data.realmModule
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.mongodb.ext.profileAsBsonDocument
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import javax.inject.Singleton

class InvitationRecord() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var studentEmailId: String =""
    var courseId:String = ""
    var courseName: String=""
    var invitedByTeacherEmail: String=""
    var status: String="sent"
    var timeOfInviting: String=""
}

class StudentRecord : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var markedByTeacherId: String = ""
    var courseId: String=""
    var studentEmailId: String =""
    var classNumber:Int = 0
    var isPresent: Boolean = false
    var timeOfAttendance: String=""
}

//data class ClassAttendance(
//    var date: Date,
//    var attendanceRecord: MutableList<StudentRecord> = mutableListOf()
//)

//class ClassAttendance : RealmObject {
//    @PrimaryKey
//    var _id: ObjectId = ObjectId()
//    var date: String = ""
//    var attendanceRecord: RealmList<StudentRecord> = realmListOf()
//    val courseAttendance: RealmResults<Course> by backlinks(Course::courseAttendances)
//}

@Singleton
class ClassAttendanceManager {
//    private val attendances = ClassAttendance()
    //    private val config = RealmConfiguration.Builder(schema = setOf(Course::class))
//        .build()
//    private val realm = Realm.open(config)
//    suspend fun example(){
//        realm.write {
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
//    }
//    fun createAttendanceRecord(_id: ObjectId):ObjectId {
//        val realm = realmModule.realm
//        var classAttendance = ClassAttendance()
//        realm.writeBlocking {
////            val objectId = ObjectId(_id.encodeToByteArray()) // or ObjectId("5f1f3c7e7c8d2d2a60e9b4f3")
//            val course = this.query<Course>("_id == $0", _id ).first().find()
//            classAttendance = ClassAttendance().apply {
//                date = Date().toString()
//            }
//            course?.courseAttendances?.add(classAttendance)
//        }
//        Log.d("Course Created ::","courese ${classAttendance._id}")
//        return classAttendance._id
//    }
    fun getAllStudentRecords(){
        val realm = realmModule.realm
        val user_id = app.currentUser?.id
        if(!user_id.isNullOrBlank()){
            var courses = realm.query<Course>("$0 IN enrolledStudents", ObjectId(user_id)).find()
    //        var courses = realm.query<Course>().find()
            Log.d("YRR -- "," - ${courses.count()}")
            courses.forEach{course ->
                Log.d("milee hau bhai ---  ::", "${course.name}")
            }
            realm.writeBlocking {
                val course = this.query<Course>("_id == $0", courses[0]._id ).first().find()
                val studend_id = app.currentUser?.id
                if (!studend_id.isNullOrBlank()) {
                    course?.enrolledStudent?.add(studend_id)
                }
            }

        }

//        var enrolledStudents = realm.query<ClassAttendance>().find()
//        this.copyToRealm(course)
//        val user = realm.query<UserRole>("user_id == $0",
//            app.currentUser?.id?.let { ObjectId(it) }).first().find()

    //            doesStudentExist = course?.enrolledStudents?.any { it.email == studentEmail } == true
    //            if (doesStudentExist != true) {
    //                studentToEnroll = EnrolledStudent().apply {
    //                    name = studentName
    //                    email = studentEmail
    //                    date = Date().toString()
    //                }


//        Log.d("user", "user :: ${user}")
//        Log.d("class attendande ---  ::","$enrolledStudents")
//        Log.d(" Users ---  ::","$users")
//        app.currentUser.functions
        Log.d("Current User ---  ::","${app.currentUser?.profileAsBsonDocument()}")
    }

    fun enrollStudent(_id: ObjectId){

        val realm = realmModule.realm
//        var studentToEnroll = EnrolledStudent()
        var doesStudentExist = false
        realm.writeBlocking {
            val course = this.query<Course>("_id == $0", _id ).first().find()
//            doesStudentExist = course?.enrolledStudents?.any { it.email == studentEmail } == true
//            if (doesStudentExist != true) {
//                studentToEnroll = EnrolledStudent().apply {
//                    name = studentName
//                    email = studentEmail
//                    date = Date().toString()
//                }
            val studend_id = app.currentUser?.id
            if(!studend_id.isNullOrBlank()){
                course?.enrolledStudents?.add(ObjectId(studend_id))
            }
//            }
        }
        if(doesStudentExist){
            Log.d("Error ::","Student Already Exits")
        }
    }

//    suspend fun addStudentRecord(_id:ObjectId, emailid: String, llogStatus:String) {
//        val realm = realmModule.realm
//        realm.write{
////            val course = this.query<Course>("_id == $0",_id).first().find()
//            val classAttendance = this.query<ClassAttendance>("_id == $0",_id).first().find()
//            val studentRecord = StudentRecord().apply {
//                emailId = emailid
//                isPresent = true
//                logStatus = llogStatus
//                timeOfAttendance = Date().toString()
//            }
//            classAttendance?.attendanceRecord?.add(studentRecord)
//        }
//    }

//    suspend fun getClassAttendanceRecord(_id: ObjectId): ClassAttendance? {
//        val realm = realmModule.realm
//        var classAttendance=realm.query<ClassAttendance>("_id == $0",_id).first().find()
//        return classAttendance
//    }


//
//    fun removeStudentRecord(StudentRecord: StudentRecord) {
//        attendances.attendanceRecord.remove(StudentRecord)
//    }
//
//    fun updateStudentRecord(StudentRecord: StudentRecord) {
//        val index = attendances.attendanceRecord.indexOf(StudentRecord)
//        if (index != -1) {
//            attendances.attendanceRecord[index] = StudentRecord
//        }
//    }
//
//    fun getStudentRecord(emailId: String): StudentRecord? {
//        return attendances.attendanceRecord.find { it.emailId == emailId }
//    }
//
//    fun getAttendance(): ClassAttendance {
//        return attendances
//    }
}



