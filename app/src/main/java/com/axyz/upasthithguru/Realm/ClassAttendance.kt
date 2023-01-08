package com.axyz.upasthithguru.Realm

import java.util.Date

data class StudentRecord(
    var emailId: String,
    var isPresent: Boolean,
    var logStatus: String,
    var timeOfAttendance: Date
)

data class ClassAttendance(
    var date: Date,
    var attendanceRecord: MutableList<StudentRecord> = mutableListOf()
)

class ClassAttendanceManager {
    private val attendances = ClassAttendance(Date(System.currentTimeMillis()))

    fun addStudentRecord(StudentRecord: StudentRecord) {
        if(!attendances.attendanceRecord.contains(StudentRecord)){
            attendances.attendanceRecord.add(StudentRecord)
        }
    }

    fun removeStudentRecord(StudentRecord: StudentRecord) {
        attendances.attendanceRecord.remove(StudentRecord)
    }

    fun updateStudentRecord(StudentRecord: StudentRecord) {
        val index = attendances.attendanceRecord.indexOf(StudentRecord)
        if (index != -1) {
            attendances.attendanceRecord[index] = StudentRecord
        }
    }

    fun getStudentRecord(emailId: String): StudentRecord? {
        return attendances.attendanceRecord.find { it.emailId == emailId }
    }

    fun getAttendance(): ClassAttendance {
        return attendances
    }
}

