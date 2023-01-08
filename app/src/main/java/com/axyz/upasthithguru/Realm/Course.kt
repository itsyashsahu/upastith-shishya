package com.axyz.upasthithguru.Realm

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.*


open class Course() : RealmObject {
    @PrimaryKey var id: String = ""
    var name: String = ""
    private var courseCode: String = ""
    var courseDescription: String = ""
    var courseCredits: String = ""
    var courseDepartment: String = ""
    var courseYear: String = ""
    var courseSemester: String = ""
    var serviceIdForInstructor: String = ""
    val instructors: RealmList<Instructor> = realmListOf()
    val enrolledStudentsData: MutableList<EnrolledStudent> = mutableListOf()
    val courseAttendance: MutableList<ClassAttendance> = mutableListOf()

    constructor(
        id: String,
        name: String,
        courseCode: String,
        courseDescription: String,
        courseCredits: String,
        courseDepartment: String,
        courseYear: String,
        courseSemester: String,
        serviceIdForInstructor: String,
    ) : this() {
        this.id = id
        this.name = name
        this.courseCode = courseCode
        this.courseDescription = courseDescription
        this.courseCredits = courseCredits
        this.courseDepartment = courseDepartment
        this.courseYear = courseYear
        this.courseSemester = courseSemester
        this.serviceIdForInstructor = serviceIdForInstructor
    }

}

@Suppress("IfThenToElvis")
class CourseManager {
    private val config = RealmConfiguration.Builder(schema = setOf(Course::class))
        .build()
    private val realm = Realm.open(config)

    fun insertCourse(course: Course) {
        try {
            realm.writeBlocking {
                this.copyToRealm(course)
            }
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Inserting Course Failed - $e")
        }
    }

    fun updateCourse(
        courseId: String,
        name: String? = null,
        courseDescription: String? = null,
        courseCredits: String? = null,
        courseDepartment: String? = null,
        courseYear: String? = null,
        courseSemester: String? = null,
        serviceIdForInstructor: String? = null,
    ) {
        try {
            realm.writeBlocking {
                val course: Course? = this.query<Course>("_id == $0", courseId).first().find()
                if (course == null) {
                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
                } else {
                    if (name != null) {
                        course.name = name
                    }
                    if (courseDescription != null) {
                        course.courseDescription = courseDescription
                    }
                    if (courseCredits != null) {
                        course.courseCredits = courseCredits
                    }
                    if (courseDepartment != null) {
                        course.courseDepartment = courseDepartment
                    }
                    if (courseYear != null) {
                        course.courseYear = courseYear
                    }
                    if (courseSemester != null) {
                        course.courseSemester = courseSemester
                    }
                    if (serviceIdForInstructor != null) {
                        course.serviceIdForInstructor = serviceIdForInstructor
                    }
                    Log.d("Database Update :: ", "Updated Course Successfully")
                }
            }
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Updating Course Failed - $e")
        }
    }

    // TODO
    // need to change this function to accept the Instructor Id and
    // then add that instructor to the course instead of asking for
    // the created instructor  of MAY BE NOT
    fun addInstructor(
        courseId: String,
        instructor: Instructor
    ) {
        try {
            realm.writeBlocking {
                val course: Course? = this.query<Course>("_id == $0", courseId).first().find()
                if (course == null) {
                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
                } else {
                    //TODO add Check if Student Already exits
                    course.instructors.add(instructor)
                }
            }
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Updating Course Failed - $e")
        }
    }

    fun removeInstructor(
        courseId: String,
        instructor: Instructor
    ) {
        try {
            realm.writeBlocking {
                val course: Course? = this.query<Course>("_id == $0", courseId).first().find()
                if (course == null) {
                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
                } else {
                    course.instructors.remove(instructor)
                }
            }
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Updating Course Failed - $e")
        }
    }

    fun sendInviteToStudent(
        courseId: String,
        enrolledStudent: EnrolledStudent
    ) {
        try {
            realm.writeBlocking {
                val course: Course? = this.query<Course>("_id == $0", courseId).first().find()
                if (course == null) {
                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
                } else {
                    //TODO add Check if Student Already exits
                    course.enrolledStudentsData.add(enrolledStudent)
                }
            }
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Updating Course Failed - $e")
        }
    }

    fun removeStudent(
        courseId: String,
        enrolledStudent: EnrolledStudent
    ) {
        try {
            realm.writeBlocking {
                val course: Course? = this.query<Course>("_id == $0", courseId).first().find()
                if (course == null) {
                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
                } else {
                    course.enrolledStudentsData.remove(enrolledStudent)
                }
            }
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Updating Course Failed - $e")
        }
    }

    fun addAttendance(courseId: String, attendance: ClassAttendance) {
        try {
            realm.writeBlocking {
                val course: Course? = this.query<Course>("_id == $0", courseId).first().find()
                if (course == null) {
                    Log.d("DatabaseError :: ", "No Course Found for the Specified Id")
                } else {
                    //TODO add Check if Student Already exits
                    course.courseAttendance.add(attendance)
                }
            }
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Updating Course Failed - $e")
        }
    }

    fun getAttendance(courseId: String, date: Date): ClassAttendance? {
        return try {
            realm.query<Course>("_id == $0", courseId).first()
                .find()?.courseAttendance?.find { it -> it.date == date }
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Updating Course Failed - $e")
            null
        }
    }

    fun getAllAttendance(courseId: String): MutableList<ClassAttendance>? {
        return try {
            realm.query<Course>("_id == $0", courseId).first().find()?.courseAttendance
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Updating Course Failed - $e")
            null
        }
    }

    fun deleteCourse(id: String) {
        try {
            realm.writeBlocking {
                val course: Course? =
                    this.query<Course>("_id == $0", id).first().find()
                if (course != null) {
                    delete(course)
                } else {
                    Log.d("Database Error :: ", "Deleting Course Failed - Course NOT FOUND")
                }
            }
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Deleting Student Failed - $e")
        }
    }

    fun getCourse(id: String): Course? {
        return try {
            realm.query<Course>("_id == $0", id).first().find()
        } catch (e: Exception) {
            Log.d("Database Error :: ", "Failed to Get Course - $e")
            null
        }
    }

    fun getAllCourse(): RealmResults<Course> {
        return realm.query<Course>().find()
    }
}
