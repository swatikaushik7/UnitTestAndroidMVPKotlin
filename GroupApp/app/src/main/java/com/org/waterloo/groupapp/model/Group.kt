package com.org.waterloo.groupapp.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="groups")
data class Group (
    @NonNull
    @PrimaryKey
    val groupName : String,
    val groupStrength : Int
)