package com.org.waterloo.groupapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GroupDao {

    @Insert
    fun addGroup(group : Group)

    @Query("Select groupName from groups" )
    fun retrieveGroups() : LiveData<List<String>>
}