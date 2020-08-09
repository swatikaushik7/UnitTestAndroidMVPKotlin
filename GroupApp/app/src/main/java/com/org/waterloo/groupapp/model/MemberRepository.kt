package com.org.waterloo.groupapp.model

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MemberRepository (application: Application):CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var memberDao: MemberDao?
    private var groupDao : GroupDao?

    init{
        val db = MemberDatabase.getDatabaseInstance(application)
        memberDao = db?.memberDao()
        groupDao = db?.groupDao()
    }

    fun getGroups() = groupDao?.retrieveGroups()

    fun addGroup(group : Group) {
        launch{
            addGroupDetails(group)
        }
    }

    fun getMembers(groupName: String?) = memberDao?.retrieveMembers(groupName)

    fun  addMembers(member: Member){
        launch{
            addDetails(member)
        }
    }

    private suspend fun addGroupDetails(group :Group){
        withContext(Dispatchers.IO){
            groupDao?.addGroup(group)
        }
    }
    fun getMemberDetail(name:String) = memberDao?.retrieveDetails(name)
    fun deleteMember(name:String) {
        launch{
            deleteMemberRecord(name)
        }
    }

    private suspend fun addDetails(member:Member){
        withContext(Dispatchers.IO){
            memberDao?.addMemberDetails(member)
        }
    }
    private suspend fun deleteMemberRecord(name:String){
        withContext(Dispatchers.IO){
            memberDao?.deleteMember(name)
        }
    }
}