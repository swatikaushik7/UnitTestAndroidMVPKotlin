package com.org.waterloo.groupapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemberDao {

    @Insert
    fun addMemberDetails(member:Member)

    @Query("Select name from members where `group` like :groupName")
    fun retrieveMembers(groupName : String?):LiveData<List<String>>

    @Query("Select * from members where name = :name")
    fun retrieveDetails(name: String):LiveData<Member>

    @Query("Update  members Set age = :age, gender = :gender, email =:email, phoneNo =:phone where name = :name")
    fun updateDetails(age : String, gender : String ,email : String,phone:String, name : String)

    @Query("Delete from members where name = :name")
    fun deleteMember(name : String)
}