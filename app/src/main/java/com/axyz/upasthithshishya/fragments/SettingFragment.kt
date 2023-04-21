package com.axyz.upasthithshishya.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.Realm.ClassAttendanceManager
import com.axyz.upasthithshishya.Realm.InvitationRecord
import com.axyz.upasthithshishya.app
import com.axyz.upasthithshishya.data.realmModule
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.ext.call
import io.realm.kotlin.mongodb.ext.customDataAsBsonDocument
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.BsonBoolean
import org.mongodb.kbson.ObjectId

class SettingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
//            ClassAttendanceManager().getAllStudentRecords()
//            ClassAttendanceManager().enrollStudent()
            var invitations  = realmModule.realm.query<InvitationRecord>().find()
            Log.d("Invitations ::","${invitations[0]}")
            if(invitations.size > 0){
                val studentEmail = app.currentUser?.customDataAsBsonDocument()?.getValue("email")
                    ?.asString()?.value.toString()
                val functionResponse = app.currentUser?.functions
                    ?.call<BsonBoolean>("AcceptInvitation",invitations[0]._id,studentEmail,
                        invitations[0].courseId.let { ObjectId(it) }, app.currentUser!!.id.toString())
                Log.d("user", "user usha function :: ${functionResponse}")
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }
}