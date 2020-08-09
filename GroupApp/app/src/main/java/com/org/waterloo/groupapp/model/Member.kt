package com.org.waterloo.groupapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")
data class Member (
    val name : String,
    val age : String,
    val gender : String,
    val email: String,
    val phoneNo : String,
    val group :String?
){
    @PrimaryKey(autoGenerate = true ) var id : Int = 0
}